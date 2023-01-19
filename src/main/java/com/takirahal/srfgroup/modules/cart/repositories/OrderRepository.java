package com.takirahal.srfgroup.modules.cart.repositories;

import com.takirahal.srfgroup.modules.cart.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Query("select distinct ord from Order ord join ord.carts crt where crt.sellOffer.user.id= ?#{principal.id} ORDER BY ord.id DESC")
    List<Order> getReceivedOrders(Pageable pageable);
}
