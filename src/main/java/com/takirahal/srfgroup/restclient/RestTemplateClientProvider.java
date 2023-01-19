package com.takirahal.srfgroup.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestTemplateClientProvider extends RestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${dynamicsvariables.base-url-backend}")
    private String baseUrl;

    @Override
    public <T, R> ResponseEntity<R> postHttpRequest(String url, T request, ParameterizedTypeReference<R> typeReference, HttpHeaders heades) {
        HttpEntity<T> requestEntity = new HttpEntity(request, heades);
        ResponseEntity<R> responseEntity = restTemplate.exchange(baseUrl.concat(url), HttpMethod.POST, requestEntity, typeReference);
        return responseEntity;
    }

    @Override
    public <R> ResponseEntity<R> getHttpRequest(String url, ParameterizedTypeReference<R> typeReference, List<HttpHeaders> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        if( headers!= null ){
            headers.stream().forEach(head -> {
                // httpHeaders;
            });
        }

        HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<R> responseEntity = restTemplate.exchange(baseUrl.concat(url), HttpMethod.GET, httpEntity, typeReference);
        return responseEntity;
    }

    @Override
    public <T, R> R putHttpRequest(String serviceUri, T requestBody, ParameterizedTypeReference<R> typeReference, HttpHeaders headers) {
        return null;
    }

    @Override
    public <T, R> R deletetHttpRequest(String serviceUri, T requestBody, ParameterizedTypeReference<R> typeReference, HttpHeaders headers) {
        return null;
    }
}
