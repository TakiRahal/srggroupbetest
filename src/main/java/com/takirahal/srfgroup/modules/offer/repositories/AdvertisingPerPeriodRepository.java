package com.takirahal.srfgroup.modules.offer.repositories;

import com.takirahal.srfgroup.modules.offer.entities.AdvertisingPerPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisingPerPeriodRepository extends JpaRepository<AdvertisingPerPeriod, Long>, JpaSpecificationExecutor<AdvertisingPerPeriod> {
    Optional<AdvertisingPerPeriod> findByAvailable(boolean available);
}
