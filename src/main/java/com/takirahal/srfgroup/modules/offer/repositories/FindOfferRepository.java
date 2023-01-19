package com.takirahal.srfgroup.modules.offer.repositories;

import com.takirahal.srfgroup.modules.offer.entities.FindOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FindOfferRepository  extends JpaRepository<FindOffer, Long>, JpaSpecificationExecutor<FindOffer> {}
