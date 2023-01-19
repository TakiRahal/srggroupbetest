package com.takirahal.srfgroup.modules.commentoffer.controllers;

import com.takirahal.srfgroup.modules.commentoffer.dto.CommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.dto.filter.CommentOfferFilter;
import com.takirahal.srfgroup.modules.commentoffer.services.CommentOfferService;
import com.takirahal.srfgroup.modules.user.dto.UserDTO;
import com.takirahal.srfgroup.modules.user.exceptioins.AccountResourceException;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.utils.HeaderUtil;
import com.takirahal.srfgroup.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

/**
 *
 */
@RestController
@RequestMapping("/api/comment-offer/")
public class CommentOfferController {

    private final Logger log = LoggerFactory.getLogger(CommentOfferController.class);

    @Autowired
    CommentOfferService commentOfferService;

    @GetMapping("by-offer/{commentOfferId}")
    public ResponseEntity<Page<CommentOfferDTO>> getAllCommentsByOffer(
            CommentOfferFilter criteria,
            Pageable pageable,
            @PathVariable("commentOfferId") Long offerId
    ) {
        log.info("REST request to get comments by offer : {}", offerId);
        Page<CommentOfferDTO> page = commentOfferService.findByCriteria(criteria, pageable, offerId);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * {@code POST  /comment-offers} : Create a new commentOffer.
     *
     * @param commentOfferDTO the commentOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentOfferDTO, or with status {@code 400 (Bad Request)} if the commentOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<CommentOfferDTO> createCommentOfferByCurrentUser(@RequestBody CommentOfferDTO commentOfferDTO) {
        log.info("REST request to save CommentOffer : {}", commentOfferDTO);
        CommentOfferDTO result = commentOfferService.save(commentOfferDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("comment_offer.comment_offer_added_succefully", result.getId().toString()), HttpStatus.CREATED);
    }

    /**
     * {@code PUT  /comment-offers/:id} : Updates an existing commentOffer.
     *
     * @param id the id of the commentOfferDTO to save.
     * @param commentOfferDTO the commentOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentOfferDTO,
     * or with status {@code 400 (Bad Request)} if the commentOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentOfferDTO couldn't be updated.
     */
    @PutMapping("{id}")
    public ResponseEntity<CommentOfferDTO> updateCommentOffer(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CommentOfferDTO commentOfferDTO
    ) {
        log.info("REST request to update CommentOffer : {}, {}", id, commentOfferDTO);
        CommentOfferDTO result = commentOfferService.updateCommentOffer(commentOfferDTO, id);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("comment_offer.comment_offer_update_succefully", result.getId().toString()), HttpStatus.OK);
    }


    /**
     * {@code DELETE  /comment-offers/:id} : delete the "id" commentOffer.
     *
     * @param id the id of the commentOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteCommentOffer(@PathVariable Long id) {
        log.info("REST request to delete CommentOffer : {}", id);
        commentOfferService.delete(id);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("comment_offer.comment_offer_delete_succefully", id.toString()), HttpStatus.OK);
    }

}
