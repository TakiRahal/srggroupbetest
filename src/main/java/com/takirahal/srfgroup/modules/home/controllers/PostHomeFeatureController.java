package com.takirahal.srfgroup.modules.home.controllers;

import com.takirahal.srfgroup.exceptions.ResouorceNotFoundException;
import com.takirahal.srfgroup.modules.home.dto.PostHomeFeatureDTO;
import com.takirahal.srfgroup.modules.home.dto.TopHomeSlidesImageDTO;
import com.takirahal.srfgroup.modules.home.entities.PostHomeFeature;
import com.takirahal.srfgroup.modules.home.repositories.PostHomeFeatureRepository;
import com.takirahal.srfgroup.modules.home.services.PostHomeFeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post-home-feature/")
public class PostHomeFeatureController {

    private final Logger log = LoggerFactory.getLogger(PostHomeFeatureController.class);

    @Autowired
    PostHomeFeatureService postHomeFeatureService;

    @Autowired
    PostHomeFeatureRepository postHomeFeatureRepository;


    /**
     * {@code POST  /faqs} : Create a new faq.
     *
     * @param postHomeFeatureDTO the faqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faqDTO, or with status {@code 400 (Bad Request)} if the faq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("admin/create")
    public ResponseEntity<PostHomeFeatureDTO> createTopHomeSlidesImages(@RequestBody PostHomeFeatureDTO postHomeFeatureDTO) {
        log.debug("REST request to save PostHomeFeature : {}", postHomeFeatureDTO);
        PostHomeFeatureDTO result = postHomeFeatureService.save(postHomeFeatureDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("admin/list")
    public ResponseEntity<Page<PostHomeFeatureDTO>> getPostHomeFeature(Pageable pageable) {
        log.debug("REST request to get PostHomeFeature : {}");
        Page<PostHomeFeatureDTO> postHomeFeatures = postHomeFeatureService.findByCriteria(pageable);
        return new ResponseEntity<>(postHomeFeatures, HttpStatus.OK);
    }


    /**
     * {@code GET  /offers/:id} : get the "id" offer.
     *
     * @param id the id of the offerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("admin/{id}")
    public ResponseEntity<PostHomeFeatureDTO> getPostHomeFeature(@PathVariable Long id) {
        log.debug("REST request to get TopHomeSlidesImage : {}", id);
        Optional<PostHomeFeatureDTO> postHomeFeatureDTO = postHomeFeatureService.findOne(id);
        if(!postHomeFeatureDTO.isPresent()){
            throw new ResouorceNotFoundException("Not found PostHomeFeature with id");
        }
        return new ResponseEntity<>(postHomeFeatureDTO.get(), HttpStatus.OK);
    }


    /**
     *
     * @param postHomeFeatureDTO
     * @return
     */
    @PutMapping("admin/{id}")
    public ResponseEntity<PostHomeFeatureDTO> updatePostHomeFeature(@PathVariable Long id, @RequestBody PostHomeFeatureDTO postHomeFeatureDTO) {
        log.info("REST request to update PostHomeFeature : {}", postHomeFeatureDTO);
        PostHomeFeatureDTO result = postHomeFeatureService.update(id, postHomeFeatureDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    /**
     *
     * @return
     */
    @GetMapping("/public/last")
    public ResponseEntity<PostHomeFeatureDTO> geLasttPostHomeFeature() {
        log.debug("REST request to get PostHomeFeature : {}");
        Optional<PostHomeFeatureDTO> postHomeFeatureDTO = postHomeFeatureService.findLastOne();
        if(!postHomeFeatureDTO.isPresent()){
            throw new ResouorceNotFoundException("Not found PostHomeFeature with id");
        }
        return new ResponseEntity<>(postHomeFeatureDTO.get(), HttpStatus.OK);
    }
}
