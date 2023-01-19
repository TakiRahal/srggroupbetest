package com.takirahal.srfgroup.modules.suggestion.services;

import com.takirahal.srfgroup.modules.suggestion.entities.SuggestSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SuggestSearchService {

    /**
     *
     * @param suggestSearch
     * @return
     */
    SuggestSearch save(SuggestSearch suggestSearch);

    /**
     *
     * @param suggestSearches
     */
    Iterable<SuggestSearch> createBulk(List<SuggestSearch> suggestSearches);

    /**
     *
     * @param pageable
     * @return
     */
    Page<SuggestSearch> getAllPosts(Pageable pageable);
}
