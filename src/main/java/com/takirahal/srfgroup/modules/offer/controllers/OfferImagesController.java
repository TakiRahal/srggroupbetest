package com.takirahal.srfgroup.modules.offer.controllers;

import com.takirahal.srfgroup.modules.offer.entities.OfferImages;
import com.takirahal.srfgroup.modules.offer.dto.filter.OfferImagesFilter;
import com.takirahal.srfgroup.modules.offer.services.OfferImagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offer-images/")
public class OfferImagesController {

    private final Logger log = LoggerFactory.getLogger(OfferImagesController.class);


    @Autowired
    OfferImagesService offerImagesService;


    /**
     * {@code GET  /offer-images} : get all the offerImages.
     *
     * @param pageable the pagination information.
     * @param offerImagesFilter the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerImages in body.
     */
    @GetMapping("public/offer-images-content")
    public ResponseEntity<Page<OfferImages>> getListExistOfferImages(OfferImagesFilter offerImagesFilter, Pageable pageable) {
        log.debug("REST request to get OfferImages by criteria: {}", offerImagesFilter);
        Page<OfferImages> page = offerImagesService.getListExistOfferImages(offerImagesFilter, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
