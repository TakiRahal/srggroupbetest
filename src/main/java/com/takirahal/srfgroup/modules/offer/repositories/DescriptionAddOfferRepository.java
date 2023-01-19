package com.takirahal.srfgroup.modules.offer.repositories;

import com.takirahal.srfgroup.modules.offer.entities.DescriptionAddOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the DescriptionAddOffer entity.
 */
@Repository
public interface DescriptionAddOfferRepository
    extends JpaRepository<DescriptionAddOffer, Long>, JpaSpecificationExecutor<DescriptionAddOffer> {
    Optional<DescriptionAddOffer> findTopByOrderByIdDesc();
}
