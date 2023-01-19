package com.takirahal.srfgroup.modules.offer.services;

import com.takirahal.srfgroup.modules.offer.dto.DescriptionAddOfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.takirahal.srfgroup.modules.offer.dto.DescriptionAddOfferDTO}.
 */
public interface DescriptionAddOfferService {
    /**
     * Save a descriptionAddOffer.
     *
     * @param descriptionAddOfferDTO the entity to save.
     * @return the persisted entity.
     */
    DescriptionAddOfferDTO save(DescriptionAddOfferDTO descriptionAddOfferDTO);

    /**
     * Partially updates a descriptionAddOffer.
     *
     * @param descriptionAddOfferDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DescriptionAddOfferDTO> partialUpdate(DescriptionAddOfferDTO descriptionAddOfferDTO);

    /**
     * Get all the descriptionAddOffers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DescriptionAddOfferDTO> findAll(Pageable pageable);

    /**
     * Get the "id" descriptionAddOffer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DescriptionAddOfferDTO> findOne(Long id);

    /**
     *
     * @return
     */
    Optional<DescriptionAddOfferDTO> findLastPublic();

    /**
     * Delete the "id" descriptionAddOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    DescriptionAddOfferDTO update(DescriptionAddOfferDTO descriptionAddOfferDTO, Long id);

    /**
     *
     * @param pageable
     * @return
     */
    Page<DescriptionAddOfferDTO> findByCriteria(Pageable pageable);
}
