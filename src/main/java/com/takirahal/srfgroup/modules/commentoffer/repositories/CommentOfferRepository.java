package com.takirahal.srfgroup.modules.commentoffer.repositories;

import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface CommentOfferRepository extends JpaRepository<CommentOffer, Long>, JpaSpecificationExecutor<CommentOffer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CommentOffer co WHERE co.offer.id = :offerId")
    void deleteCommentsByOfferId(@Param("offerId") Long offerId);
}
