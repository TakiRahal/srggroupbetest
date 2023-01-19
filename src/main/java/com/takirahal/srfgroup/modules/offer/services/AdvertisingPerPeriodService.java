package com.takirahal.srfgroup.modules.offer.services;

import com.takirahal.srfgroup.modules.offer.dto.AdvertisingPerPeriodDTO;
import com.takirahal.srfgroup.modules.offer.dto.OfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdvertisingPerPeriodService {

    /**
     *
     * @param advertising
     * @return
     */
    AdvertisingPerPeriodDTO save(AdvertisingPerPeriodDTO advertising);

    /**
     *
     * @param pageable
     * @return
     */
    Page<AdvertisingPerPeriodDTO> findByCriteria(Pageable pageable);

    /**
     *
     * @param id
     */
    void unavailable(Long id);

    /**
     *
     * @return
     */
    OfferDTO getAdvertisingPerPeriod();
}
