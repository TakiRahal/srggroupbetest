package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.dto.FindOfferDTO;
import com.takirahal.srfgroup.modules.offer.dto.filter.FindOfferFilter;
import com.takirahal.srfgroup.modules.offer.services.FindOfferService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/find-offer/")
public class FindOfferController {

    private final Logger log = LoggerFactory.getLogger(FindOfferController.class);

    @Autowired
    FindOfferService findOfferService;

    /**
     * {@code POST  /find-offers} : Create a new findOffer.
     *
     * @param findOfferDTO the findOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new findOfferDTO, or with status {@code 400 (Bad Request)} if the findOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("create")
    public ResponseEntity<FindOfferDTO> createFindOffer(@RequestBody FindOfferDTO findOfferDTO) {
        log.debug("REST request to save FindOffer : {}", findOfferDTO);
        FindOfferDTO result = findOfferService.save(findOfferDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_create_offer_succefull", ""), HttpStatus.CREATED);
    }


    /**
     *
     * @param findOfferFilter
     * @param pageable
     * @return
     */
    @GetMapping("public")
    public ResponseEntity<Page<FindOfferDTO>> getAllOffersForSell(FindOfferFilter findOfferFilter, Pageable pageable) {
        log.debug("REST request to get SellOffers public by criteria: {}", findOfferFilter);
        Page<FindOfferDTO> page = findOfferService.findByCriteria(findOfferFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * {@code PUT  /find-offers/:id} : Updates an existing findOffer.
     *
     * @param id the id of the findOfferDTO to save.
     * @param findOfferDTO the findOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated findOfferDTO,
     * or with status {@code 400 (Bad Request)} if the findOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the findOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("{id}")
    public ResponseEntity<FindOfferDTO> updateFindOffer(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody FindOfferDTO findOfferDTO
    ) {
        log.info("REST request to update FindOffer : {}, {}", id, findOfferDTO);
        FindOfferDTO result = findOfferService.updateFindOffer(findOfferDTO, id);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_update_offer_succefull", ""), HttpStatus.OK);
    }
}
