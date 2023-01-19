package com.takirahal.srfgroup.modules.newsletter.services.impl;

import com.takirahal.srfgroup.exceptions.BadRequestAlertException;
import com.takirahal.srfgroup.modules.newsletter.dto.filter.NewsLetterFilter;
import com.takirahal.srfgroup.modules.newsletter.entities.NewsLetter;
import com.takirahal.srfgroup.modules.newsletter.repositories.NewsLetterRepository;
import com.takirahal.srfgroup.modules.newsletter.services.NewsLetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsLetterServiceImpl implements NewsLetterService {

    private final Logger log = LoggerFactory.getLogger(NewsLetterServiceImpl.class);


    @Autowired
    NewsLetterRepository newsLetterRepository;

    @Override
    public NewsLetter save(NewsLetter newsLetter) {
        log.debug("Request to save NewsLetter : {}", newsLetter);
        if (newsLetter.getId() != null) {
            throw new BadRequestAlertException("A new NewsLetter cannot already have an ID idexists");
        }

        Optional<NewsLetter> newsLetterExist = newsLetterRepository.findByEmail(newsLetter.getEmail());
        if( newsLetterExist.isPresent() ){
            return newsLetterExist.get();
        }
        return newsLetterRepository.save(newsLetter);
    }

    @Override
    public Page<NewsLetter> findByCriteria(NewsLetterFilter criteria, Pageable pageable) {
        return newsLetterRepository.findAll(pageable);
    }
}
