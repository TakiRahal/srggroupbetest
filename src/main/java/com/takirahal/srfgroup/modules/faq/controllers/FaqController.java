package com.takirahal.srfgroup.modules.faq.controllers;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.faq.services.FaqService;
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

@RestController
@RequestMapping("/api/faq/")
public class FaqController {

    private final Logger log = LoggerFactory.getLogger(FaqController.class);

    @Autowired
    FaqService faqService;


    /**
     * {@code POST  /faqs} : Create a new faq.
     *
     * @param faqDTO the faqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faqDTO, or with status {@code 400 (Bad Request)} if the faq has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("admin/create")
    public ResponseEntity<FaqDTO> createFaq(@RequestBody FaqDTO faqDTO) throws URISyntaxException {
        log.info("REST request to save Faq : {}", faqDTO);
        if (faqDTO.getId() != null) {
            throw new BadRequestAlertException("A new faq cannot already have an ID idexists");
        }
        FaqDTO result = faqService.save(faqDTO);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    /**
     * {@code GET  /faqs} : get all the faqs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faqs in body.
     */
    @GetMapping("public")
    public ResponseEntity<Page<FaqDTO>> getAllPublicFaqs(FaqFilter criteria, Pageable pageable) {
        log.info("REST request to get Faqs by criteria: {}", criteria);
        Page<FaqDTO> page = faqService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
