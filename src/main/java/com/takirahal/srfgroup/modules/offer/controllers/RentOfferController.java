package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.dto.RentOfferDTO;
import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.offer.dto.filter.RentOfferFilter;
import com.takirahal.srfgroup.modules.offer.services.RentOfferService;
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
@RequestMapping("/api/rent-offer/")
public class RentOfferController {

    private final Logger log = LoggerFactory.getLogger(RentOfferController.class);

    @Autowired
    RentOfferService rentOfferService;

    /**
     * {@code POST  /rent-offers} : Create a new rentOffer.
     *
     * @param rentOfferDTO the rentOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentOfferDTO, or with status {@code 400 (Bad Request)} if the rentOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<RentOfferDTO> createRentOffer(@RequestBody RentOfferDTO rentOfferDTO) {
        log.debug("REST request to save RentOffer : {}", rentOfferDTO);
        RentOfferDTO result = rentOfferService.save(rentOfferDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_create_offer_succefull", ""), HttpStatus.CREATED);
    }


    /**
     *
     * @param rentOfferFilter
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<RentOfferDTO>> getAllOffersForRent(RentOfferFilter rentOfferFilter, Pageable pageable) {
        log.debug("REST request to get SellOffers public by criteria: {}", rentOfferFilter);
        Page<RentOfferDTO> page = rentOfferService.findByCriteria(rentOfferFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    /**
     * {@code PUT  /rent-offers/:id} : Updates an existing rentOffer.
     *
     * @param id the id of the rentOfferDTO to save.
     * @param rentOfferDTO the rentOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentOfferDTO,
     * or with status {@code 400 (Bad Request)} if the rentOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rentOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("{id}")
    public ResponseEntity<RentOfferDTO> updateRentOffer(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody RentOfferDTO rentOfferDTO
    ) {
        log.info("REST request to update RentOffer : {}, {}", id, rentOfferDTO);
        RentOfferDTO result = rentOfferService.updateRentOffer(rentOfferDTO, id);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_update_offer_succefull", ""), HttpStatus.OK);
    }
}
