package com.takirahal.srfgroup.modules.newsletter.controllers;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.faq.dto.FaqDTO;
import com.takirahal.srfgroup.modules.faq.dto.filter.FaqFilter;
import com.takirahal.srfgroup.modules.newsletter.dto.filter.NewsLetterFilter;
import com.takirahal.srfgroup.modules.newsletter.entities.NewsLetter;
import com.takirahal.srfgroup.modules.newsletter.services.NewsLetterService;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news-letter/")
public class NewsLetterController {
    private final Logger log = LoggerFactory.getLogger(NewsLetterController.class);

    @Autowired
    NewsLetterService newsLetterService;

    /**
     * {@code POST  /faqs} : Create a new faq.
     *
     * @param newsLetter the faqDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faqDTO, or with status {@code 400 (Bad Request)} if the faq has already an ID.
     */
    @PostMapping("public/create")
    public ResponseEntity<NewsLetter> createFaq(@RequestBody NewsLetter newsLetter) {
        log.debug("REST request to save NewsLetter : {}", newsLetter);
        NewsLetter result = newsLetterService.save(newsLetter);
        return new ResponseEntity<>(result, HeaderUtil.createAlert("news_letter.added_success", result.getId().toString()), HttpStatus.CREATED);
    }

    /**
     * {@code GET  /faqs} : get all the faqs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faqs in body.
     */
    @GetMapping("admin")
    public ResponseEntity<Page<NewsLetter>> getAllPublicFaqs(NewsLetterFilter criteria, Pageable pageable) {
        log.debug("REST request to get NewsLetter by criteria: {}", criteria);
        Page<NewsLetter> page = newsLetterService.findByCriteria(criteria, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
