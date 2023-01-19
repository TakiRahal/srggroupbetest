package com.takirahal.srfgroup.modules.offer.repositories;

import com.takirahal.srfgroup.modules.offer.entities.OfferImages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface OfferImagesRepository extends JpaRepository<OfferImages, Long>, JpaSpecificationExecutor<OfferImages> {

    @Query("SELECT DISTINCT(offImgs.offer.id), offImgs.path FROM OfferImages offImgs INNER JOIN offImgs.offer o")
    Page<OfferImages> getListExistOfferImages(Pageable pageabe);

    @Transactional
    @Modifying
    @Query("DELETE FROM OfferImages oi WHERE oi.offer.id = :offerId")
    void deleteImagesByOfferId(@Param("offerId") Long offerId);
}
