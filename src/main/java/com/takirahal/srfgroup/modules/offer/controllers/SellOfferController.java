package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.dto.SellOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.offer.dto.filter.SellOfferFilter;
import com.takirahal.srfgroup.modules.offer.services.SellOfferService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/sell-offer/")
public class SellOfferController {


    private final Logger log = LoggerFactory.getLogger(SellOfferController.class);

    @Autowired
    SellOfferService sellOfferService;

    /**
     * {@code POST  /sell-offers} : Create a new sellOffer.
     *
     * @param sellOfferDTO the sellOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sellOfferDTO, or with status {@code 400 (Bad Request)} if the sellOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<SellOfferDTO> createSellOffer(@RequestBody SellOfferDTO sellOfferDTO){
        log.info("REST request to save SellOffer : {}", sellOfferDTO);
        SellOfferDTO result = sellOfferService.save(sellOfferDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_create_offer_succefull", ""), HttpStatus.CREATED);
    }

    /**
     *
     * @param sellOfferFilter
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<SellOfferDTO>> getAllOffersForSell(SellOfferFilter sellOfferFilter, Pageable pageable) {
        log.info("REST request to get SellOffers public by criteria: {}", sellOfferFilter);
        Page<SellOfferDTO> page = sellOfferService.findByCriteria(sellOfferFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * {@code PUT  /sell-offers/:id} : Updates an existing sellOffer.
     *
     * @param id the id of the sellOfferDTO to save.
     * @param sellOfferDTO the sellOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sellOfferDTO,
     * or with status {@code 400 (Bad Request)} if the sellOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sellOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("{id}")
    public ResponseEntity<SellOfferDTO> updateSellOffer(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody SellOfferDTO sellOfferDTO
    ) {
        log.info("REST request to update SellOffer : {}, {}", id, sellOfferDTO);
        SellOfferDTO result = sellOfferService.updateSellOffer(sellOfferDTO, id);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_update_offer_succefull", ""), HttpStatus.OK);
    }

}
