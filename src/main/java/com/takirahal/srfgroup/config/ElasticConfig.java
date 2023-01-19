package com.takirahal.srfgroup.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
// @EnableElasticsearchRepositories
@ConditionalOnProperty(
        value="elasticsearch.available",
        havingValue = "true",
        matchIfMissing = false)
public class ElasticConfig {

    @Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

//    @Value("${elasticsearch.clustername}")
//    private String EsClusterName;

    @Bean
    public ElasticsearchClient getElasticSearchClient() {

        RestClient restClient = RestClient
                .builder(new HttpHost(EsHost, EsPort))
//                .setDefaultHeaders(new Header[]{
//                        new BasicHeader("Content-type", "application/json")
//                })
                .build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // And create the API client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        return client;
    }

}
