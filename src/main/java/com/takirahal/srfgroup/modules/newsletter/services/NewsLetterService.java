package com.takirahal.srfgroup.modules.newsletter.services;

import com.takirahal.srfgroup.modules.newsletter.dto.filter.NewsLetterFilter;
import com.takirahal.srfgroup.modules.newsletter.entities.NewsLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsLetterService {

    /**
     *
     * @param newsLetter
     * @return
     */
    NewsLetter save(NewsLetter newsLetter);

    Page<NewsLetter> findByCriteria(NewsLetterFilter criteria, Pageable pageable);
}
