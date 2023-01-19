package com.takirahal.srfgroup.modules.commentoffer.repositories;

import com.takirahal.srfgroup.modules.commentoffer.entities.CommentOffer;
import com.takirahal.srfgroup.modules.commentoffer.entities.ReportCommentOffer;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCommentOfferRepository extends JpaRepository<ReportCommentOffer, Long> {

    boolean existsByCommentOfferAndUser(CommentOffer commentOffer, User user);

    @Query("SELECT COUNT(re) from ReportCommentOffer re where re.commentOffer.id = :commentId")
    long nbeReportedByCommentOfferId(@Param("commentId") Long commentId);
}
