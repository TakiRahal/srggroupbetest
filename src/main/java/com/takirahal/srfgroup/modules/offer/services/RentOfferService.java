package com.takirahal.srfgroup.modules.offer.services;

import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.filter.RentOfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentOfferService {

    /**
     * Save a rentOffer.
     *
     * @param rentOfferDTO the entity to save.
     * @return the persisted entity.
     */
    RentOfferDTO save(RentOfferDTO rentOfferDTO);

    Page<RentOfferDTO> findByCriteria(RentOfferFilter rentOfferFilter, Pageable pageable);

    RentOfferDTO updateRentOffer(RentOfferDTO rentOfferDTO, Long id);
}
