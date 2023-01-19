package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.offer.dto.DescriptionAddOfferDTO;
import com.takirahal.srfgroup.modules.offer.services.DescriptionAddOfferService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.takirahal.srfgroup.modules.offer.entities.DescriptionAddOffer}.
 */
@RestController
@RequestMapping("/api/description-add-offers/")
public class DescriptionAddOfferController {

    private final Logger log = LoggerFactory.getLogger(DescriptionAddOfferController.class);

    @Autowired
    DescriptionAddOfferService descriptionAddOfferService;

    /**
     * {@code POST  /description-add-offers} : Create a new descriptionAddOffer.
     *
     * @param descriptionAddOfferDTO the descriptionAddOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new descriptionAddOfferDTO, or with status {@code 400 (Bad Request)} if the descriptionAddOffer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("admin/create")
    public ResponseEntity<DescriptionAddOfferDTO> createDescriptionAddOffer(@RequestBody DescriptionAddOfferDTO descriptionAddOfferDTO){
        log.info("REST request to save DescriptionAddOffer : {}", descriptionAddOfferDTO);
        DescriptionAddOfferDTO result = descriptionAddOfferService.save(descriptionAddOfferDTO);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("DescriptionAddOffer created successfully", ""), HttpStatus.CREATED);
    }

    /**
     * {@code PUT  /description-add-offers/:id} : Updates an existing descriptionAddOffer.
     *
     * @param id the id of the descriptionAddOfferDTO to save.
     * @param descriptionAddOfferDTO the descriptionAddOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated descriptionAddOfferDTO,
     * or with status {@code 400 (Bad Request)} if the descriptionAddOfferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the descriptionAddOfferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("admin/{id}")
    public ResponseEntity<DescriptionAddOfferDTO> updateDescriptionAddOffer(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody DescriptionAddOfferDTO descriptionAddOfferDTO ) {
        log.info("REST request to update DescriptionAddOffer : {}, {}", id, descriptionAddOfferDTO);
        DescriptionAddOfferDTO result = descriptionAddOfferService.update(descriptionAddOfferDTO, id);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("DescriptionAddOffer update succefully", result.getId().toString()), HttpStatus.OK);
    }


    /**
     * {@code GET  /description-add-offers} : get all the descriptionAddOffers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of descriptionAddOffers in body.
     */
    @GetMapping("admin/list")
    public ResponseEntity<Page<DescriptionAddOfferDTO>> getAllDescriptionAddOffers(Pageable pageable) {
        log.info("REST request to get DescriptionAddOffers by criteria: {}");
        Page<DescriptionAddOfferDTO> page = descriptionAddOfferService.findByCriteria(pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * {@code GET  /description-add-offers/:id} : get the "id" descriptionAddOffer.
     *
     * @param id the id of the descriptionAddOfferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descriptionAddOfferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("admin/{id}")
    public ResponseEntity<DescriptionAddOfferDTO> getDescriptionAddOffer(@PathVariable Long id) {
        log.info("REST request to get DescriptionAddOffer : {}", id);
        Optional<DescriptionAddOfferDTO> descriptionAddOfferDTO = descriptionAddOfferService.findOne(id);
        return new ResponseEntity<>(descriptionAddOfferDTO.get(), HttpStatus.OK);
    }

    /**
     * {@code GET  /description-add-offers/:id} : get the "id" descriptionAddOffer.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the descriptionAddOfferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("public/last")
    public ResponseEntity<DescriptionAddOfferDTO> getLastPublicDescriptionAddOffer() {
        log.info("REST request to get last public DescriptionAddOffer : {}", "getLastPublicDescriptionAddOffer");
        Optional<DescriptionAddOfferDTO> descriptionAddOfferDTO = descriptionAddOfferService.findLastPublic();
        if(!descriptionAddOfferDTO.isPresent()){
            throw new ResouorceNotFoundException("Not inseret yet from BO");
        }
        return new ResponseEntity<>(descriptionAddOfferDTO.get(), HttpStatus.OK);
    }

    /**
     * {@code DELETE  /description-add-offers/:id} : delete the "id" descriptionAddOffer.
     *
     * @param id the id of the descriptionAddOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/description-add-offers/{id}")
    public ResponseEntity<Boolean> deleteDescriptionAddOffer(@PathVariable Long id) {
        log.info("REST request to delete DescriptionAddOffer : {}", id);
        descriptionAddOfferService.delete(id);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("DescriptionAddOffer delete succefully", id.toString()), HttpStatus.OK);
    }
}
