package com.takirahal.srfgroup.modules.offer.repositories;

import com.takirahal.srfgroup.modules.offer.entities.SellOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SellOfferRepository extends JpaRepository<SellOffer, Long>, JpaSpecificationExecutor<SellOffer> {
}
