package com.takirahal.srfgroup.modules.commentoffer.services;

import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.dto.filter.CommentOfferFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentOfferService {

    /**
     * Save a commentOffer.
     *
     * @param commentOfferDTO the entity to save.
     * @return the persisted entity.
     */
    CommentOfferDTO save(CommentOfferDTO commentOfferDTO);

    /**
     *
     * @param criteria
     * @param pageable
     * @return
     */
    Page<CommentOfferDTO> findByCriteria(CommentOfferFilter criteria, Pageable pageable, Long commentOfferId);

    /**
     *
     * @param commentOfferDTO
     * @param id
     * @return
     */
    CommentOfferDTO updateCommentOffer(CommentOfferDTO commentOfferDTO, Long id);

    /**
     * Delete the "id" commentOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
