package com.takirahal.srfgroup.modules.commentoffer.controllers;

import com.takirahal.srfgroup.modules.commentoffer.dto.ReportCommentOfferDTO;
import com.takirahal.srfgroup.modules.commentoffer.services.ReportCommentOfferService;
import com.takirahal.srfgroup.modules.offer.controllers.ReportOfferController;
import com.takirahal.srfgroup.modules.offer.dto.ReportOfferDTO;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link com.takirahal.srfgroup.modules.commentoffer.entities.ReportCommentOffer}.
 */
@RestController
@RequestMapping("/api/reportcommentoffer/")
public class ReportCommentOfferController {

    private final Logger log = LoggerFactory.getLogger(ReportCommentOfferController.class);

    @Autowired
    ReportCommentOfferService reportCommentOfferService;

    /**
     * {@code POST  /favorites} : Create a new favorite.
     *
     * @param reportCommentOfferDTO the reportOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoriteDTO, or with status {@code 400 (Bad Request)} if the favorite has already an ID.
     */
    @PostMapping("create")
    public ResponseEntity<ReportCommentOfferDTO> createReportCommentOffer(@RequestBody ReportCommentOfferDTO reportCommentOfferDTO) {
        log.info("REST request to save Report CommentOffer : {}", reportCommentOfferDTO);
        ReportCommentOfferDTO result = reportCommentOfferService.save(reportCommentOfferDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("Reported Comment Offer succeffully", result.getId().toString()), HttpStatus.CREATED);
    }
}
