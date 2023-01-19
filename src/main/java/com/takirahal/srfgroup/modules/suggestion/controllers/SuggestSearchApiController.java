package com.takirahal.srfgroup.modules.suggestion.controllers;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.takirahal.srfgroup.modules.suggestion.entities.SuggestSearch;
import com.takirahal.srfgroup.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/suggest-search/client-api/")
@ConditionalOnProperty(
        value="elasticsearch.available",
        havingValue = "true",
        matchIfMissing = false)
public class SuggestSearchApiController {

    private final Logger log = LoggerFactory.getLogger(SuggestSearchRepositoryController.class);

    private static final String SUGGEST_SEARCH_INDEX = "suggest_search";

    @Autowired
    ElasticsearchClient elasticsearchClient;


    /**
     *
     * @param suggestSearch
     * @return
     */
    @PostMapping("public/create")
    public ResponseEntity<IndexResponse> createSuggestSearch(@RequestBody SuggestSearch suggestSearch) throws IOException {
        log.info("REST request to save SuggestSearch : {}", suggestSearch);
        IndexRequest<SuggestSearch> request = IndexRequest.of(i -> i
                .index(SUGGEST_SEARCH_INDEX)
                .id(suggestSearch.getId())
                .document(suggestSearch)
        );

        IndexResponse response = elasticsearchClient.index(request);
        return new ResponseEntity<>(response, HeaderUtil.createAlert("add_offer.message_create_offer_succefull", ""), HttpStatus.CREATED);
    }


    @PostMapping("public/create-bulk")
    public ResponseEntity<BulkResponse> createBulkSuggestSearch(@RequestBody List<SuggestSearch> suggestSearches) throws IOException {
        log.info("REST request to save bulk SuggestSearch : {}", suggestSearches);
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (SuggestSearch suggestSearch : suggestSearches) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(SUGGEST_SEARCH_INDEX)
                            .id(suggestSearch.getId())
                            .document(suggestSearch)
                    )
            );
        }
        BulkResponse result = elasticsearchClient.bulk(br.build());
        return new ResponseEntity<>(result, HeaderUtil.createAlert("add_offer.message_create_offer_succefull", ""), HttpStatus.CREATED);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping("public/fetchAll")
    public ResponseEntity<List<SuggestSearch>> getAllSuggestSearch(String searchText, Pageable pageable) throws IOException {
        log.info("REST request to get SuggestSearch by criteria: {}", pageable);

        // With term
//        SearchResponse<SuggestSearch> search = elasticsearchClient.search(s -> s
//                        .index(SUGGEST_SEARCH_INDEX)
//                        .query(q -> q.term(t -> t
//                                        .field("name")
//                                        .value(v -> v.stringValue("name 0"))
//                                )),
//                SuggestSearch.class);

        // With multi_match
//        SearchResponse<SuggestSearch> search = elasticsearchClient.search(s -> s
//                        .index(SUGGEST_SEARCH_INDEX)
//                        .query(q -> q.multiMatch(t -> t
//                                .fields("name", "description")
//                                .query(searchText))
//                        ),
//                SuggestSearch.class
//        );


        // With match
        SearchResponse<SuggestSearch> search = elasticsearchClient.search(s -> s
                        .index(SUGGEST_SEARCH_INDEX)
                        .query(q -> q
                                .match(t -> t
                                        .field("name")
                                        .query(searchText)
                                )
                        ),
                SuggestSearch.class
        );

        TotalHits total = search.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;
        log.info("isExactResult: {} ", isExactResult);

        // List<Hit<SuggestSearch>> hits = search.hits().hits();
        List<SuggestSearch> result = new ArrayList<>();
        for (Hit<SuggestSearch> hit: search.hits().hits()) {
            SuggestSearch suggestSearch = hit.source();
            result.add(suggestSearch);
            log.info("Found suggest_search " + suggestSearch.getId() + ", score " + hit.score());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
