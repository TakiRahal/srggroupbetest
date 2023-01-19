package com.takirahal.srfgroup.modules.offer.controllers;


import com.takirahal.srfgroup.modules.offer.dto.ReportOfferDTO;
import com.takirahal.srfgroup.modules.offer.services.ReportOfferService;
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

import java.net.URISyntaxException;

/**
 * REST controller for managing {@link com.takirahal.srfgroup.modules.offer.entities.ReportOffer}.
 */
@RestController
@RequestMapping("/api/reportoffer/")
public class ReportOfferController {

    private final Logger log = LoggerFactory.getLogger(ReportOfferController.class);

    @Autowired
    ReportOfferService reportOfferService;

    /**
     * {@code POST  /favorites} : Create a new favorite.
     *
     * @param reportOfferDTO the reportOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favoriteDTO, or with status {@code 400 (Bad Request)} if the favorite has already an ID.
     */
    @PostMapping("create")
    public ResponseEntity<ReportOfferDTO> createReportOffer(@RequestBody ReportOfferDTO reportOfferDTO) {
        log.debug("REST request to save ReportOffer : {}", reportOfferDTO);
        ReportOfferDTO result = reportOfferService.save(reportOfferDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("Reported offer succeffully", result.getId().toString()), HttpStatus.CREATED);
    }
}
