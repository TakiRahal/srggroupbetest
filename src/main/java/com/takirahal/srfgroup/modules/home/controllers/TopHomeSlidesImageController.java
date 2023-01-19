package com.takirahal.srfgroup.modules.home.controllers;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.home.dto.TopHomeSlidesImageDTO;
import com.takirahal.srfgroup.modules.home.services.TopHomeSlidesImageService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/top-home-slides-images/")
public class TopHomeSlidesImageController {

    private final Logger log = LoggerFactory.getLogger(TopHomeSlidesImageController.class);

    @Autowired
    TopHomeSlidesImageService topHomeSlidesImageService;

    /**
     * {@code POST  /faqs} : Create a new faq.
     *
     * @param topHomeSlidesImage the faqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faqDTO, or with status {@code 400 (Bad Request)} if the faq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("admin/create")
    public ResponseEntity<TopHomeSlidesImageDTO> createTopHomeSlidesImages(@RequestBody TopHomeSlidesImageDTO topHomeSlidesImage) {
        log.info("REST request to save TopHomeSlidesImage : {}", topHomeSlidesImage);
        TopHomeSlidesImageDTO result = topHomeSlidesImageService.save(topHomeSlidesImage);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     *
     * @param topHomeSlidesImage
     * @return
     */
    @PutMapping("admin/update/{id}")
    public ResponseEntity<TopHomeSlidesImageDTO> updateTopHomeSlidesImages(@PathVariable Long id, @RequestBody TopHomeSlidesImageDTO topHomeSlidesImage) {
        log.info("REST request to update TopHomeSlidesImage : {}", topHomeSlidesImage);
        TopHomeSlidesImageDTO result = topHomeSlidesImageService.update(id, topHomeSlidesImage);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * {@code GET  /faqs} : get all the faqs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faqs in body.
     */
    @GetMapping("admin/slides")
    public ResponseEntity<Page<TopHomeSlidesImageDTO>> getTopHomeSlidesByAdmin(Pageable pageable) {
        log.info("REST request to get TopHomeSlidesImage : {}", pageable);
        Page<TopHomeSlidesImageDTO> topHomeSlidesFullDTO = topHomeSlidesImageService.getTopHomeSlidesByAdmin(pageable);
        return new ResponseEntity<>(topHomeSlidesFullDTO, HttpStatus.OK);
    }

    /**
     * {@code GET  /faqs} : get all the faqs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faqs in body.
     */
    @GetMapping("public/slides")
    public ResponseEntity<Page<TopHomeSlidesImageDTO>> getTopHomeSlides(Pageable pageable) {
        log.info("REST request to get TopHomeSlidesImage : {}", pageable);
        Page<TopHomeSlidesImageDTO> topHomeSlidesFullDTO = topHomeSlidesImageService.findByCriteria(pageable);
        return new ResponseEntity<>(topHomeSlidesFullDTO, HttpStatus.OK);
    }


    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @param id the id of the offerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("admin/{id}")
    public ResponseEntity<TopHomeSlidesImageDTO> getTopHomeSlidesImage(@PathVariable Long id) {
        log.info("REST request to get TopHomeSlidesImage : {}", id);
        Optional<TopHomeSlidesImageDTO> topHomeSlidesImageDTO = topHomeSlidesImageService.findOne(id);
        if(!topHomeSlidesImageDTO.isPresent()){
            throw new ResouorceNotFoundException("Not found TopHomeSlidesImage with id");
        }
        return new ResponseEntity<>(topHomeSlidesImageDTO.get(), HttpStatus.OK);
    }


    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("admin/{id}")
    public ResponseEntity<Boolean> deleteTopHomeSlidesImage(@PathVariable Long id) {
        log.info("REST request to delete TopHomeSlidesImage : {}", id);
        topHomeSlidesImageService.delete(id);
        return new ResponseEntity<>(true, HeaderUtil.createAlert("TopHomeSlidesImage.TopHomeSlidesImage_delete_succefully", id.toString()), HttpStatus.OK);
    }
}
