package com.takirahal.srfgroup.modules.offer.services;

import com.takirahal.srfgroup.modules.offer.dto.FindOfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.filter.FindOfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindOfferService {

    /**
     * Save a findOffer.
     *
     * @param findOfferDTO the entity to save.
     * @return the persisted entity.
     */
    FindOfferDTO save(FindOfferDTO findOfferDTO);

    Page<FindOfferDTO> findByCriteria(FindOfferFilter findOfferFilter, Pageable pageable);

    FindOfferDTO updateFindOffer(FindOfferDTO findOfferDTO, Long id);
}
