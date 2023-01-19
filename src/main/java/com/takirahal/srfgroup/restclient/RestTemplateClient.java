package com.takirahal.srfgroup.restclient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class RestTemplateClient {

    public abstract <T, R> ResponseEntity<R> postHttpRequest(String serviceUri, T request, ParameterizedTypeReference<R> typeReference, HttpHeaders heades) ;
    public abstract <R> ResponseEntity<R> getHttpRequest(String serviceUri, ParameterizedTypeReference<R> typeReference, List<HttpHeaders> headers);
    public abstract <T, R> R putHttpRequest(String serviceUri, T requestBody, ParameterizedTypeReference<R> typeReference, HttpHeaders headers);
    public abstract <T, R> R deletetHttpRequest(String serviceUri, T requestBody, ParameterizedTypeReference<R> typeReference, HttpHeaders headers);
}
