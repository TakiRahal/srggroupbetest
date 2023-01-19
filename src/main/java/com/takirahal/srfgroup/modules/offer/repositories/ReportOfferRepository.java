package com.takirahal.srfgroup.modules.offer.repositories;

import com.takirahal.srfgroup.modules.offer.entities.Offer;
import com.takirahal.srfgroup.modules.offer.entities.ReportOffer;
import com.takirahal.srfgroup.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportOfferRepository extends JpaRepository<ReportOffer, Long> {

    boolean existsByOfferAndUser(Offer offer, User user);

    @Query("SELECT COUNT(re) from ReportOffer re where re.offer.id = :offerId")
    long nbeReportedByOfferId(@Param("offerId") Long offerId);
}
