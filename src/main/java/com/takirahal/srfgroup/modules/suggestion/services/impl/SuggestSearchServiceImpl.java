package com.takirahal.srfgroup.modules.suggestion.services.impl;

import com.takirahal.srfgroup.modules.offer.services.impl.SellOfferServiceImpl;
import com.takirahal.srfgroup.modules.suggestion.entities.SuggestSearch;
import com.takirahal.srfgroup.modules.suggestion.repositories.SuggestSearchRepository;
import com.takirahal.srfgroup.modules.suggestion.services.SuggestSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(
        value="elasticsearch.available",
        havingValue = "true",
        matchIfMissing = false)
public class SuggestSearchServiceImpl implements SuggestSearchService {

    private final Logger log = LoggerFactory.getLogger(SuggestSearchServiceImpl.class);

    @Autowired
    SuggestSearchRepository suggestSearchRepository;

    @Override
    public SuggestSearch save(SuggestSearch suggestSearch) {
        log.info("Request to save EsOffer : {}", suggestSearch);
        return suggestSearchRepository.save(suggestSearch);
    }


    @Override
    public Iterable<SuggestSearch> createBulk(List<SuggestSearch> suggestSearches) {
        return suggestSearchRepository.saveAll(suggestSearches);
    }

    @Override
    public Page<SuggestSearch> getAllPosts(Pageable pageable) {
        return suggestSearchRepository.findAll(pageable);
    }

}
