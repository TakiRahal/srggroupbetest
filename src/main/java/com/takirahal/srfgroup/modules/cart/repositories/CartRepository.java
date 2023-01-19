package com.takirahal.srfgroup.modules.cart.repositories;

import com.takirahal.srfgroup.modules.cart.entities.Cart;
import com.takirahal.srfgroup.modules.cart.entities.Order;
import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {

    @Query("SELECT COUNT(ct) from Cart ct where ct.user.id = ?#{principal.id} AND ct.sellOffer.id=:sellOfferId AND ct.status='StandBy'")
    long getCountCartBySellOfferAndUser(@Param("sellOfferId") Long sellOfferId);

    @Query("SELECT ct from Cart ct where ct.user.id =:userId AND ct.sellOffer.id=:sellOfferId AND ct.status='StandBy'")
    Optional<Cart> findBySellOfferAndUser(@Param("sellOfferId") Long sellOfferId, @Param("userId") Long userId);

    @Query(
            "SELECT COUNT(ct) FROM Cart ct WHERE ct.user.id = :userId AND ct.status='StandBy'"
    )
    long getNumberOfCarts(@Param("userId") Long userId);

    @Query("select distinct ct from Cart ct ORDER BY ct.id DESC")
    List<Cart> getMostCarts(Pageable pageable);
}
