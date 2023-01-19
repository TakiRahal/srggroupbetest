package com.takirahal.srfgroup.services.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.takirahal.srfgroup.modules.contactus.models.CaptchaResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ValidateCaptchaService {

    private final Logger log = LoggerFactory.getLogger(ValidateCaptchaService.class);

    private final RestTemplate template;

    @Value("${google.recaptcha.verification.endpoint}")
    String recaptchaEndpoint;
    @Value("${google.recaptcha.secret}")
    String recaptchaSecret;

    public ValidateCaptchaService(final RestTemplateBuilder templateBuilder) {
        this.template = templateBuilder.build();
    }

    // method validate the captcha response coming from the client
    // and return either true or false after the validation.
    // reference url - https://developers.google.com/recaptcha/docs/verify
    public boolean validateCaptcha(final String captchaResponse) {
        // "captchaResponse" length validation is omitted for brevity.

        log.info("Going to validate the captcha response = {}", captchaResponse);
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // "secret" is a required param and it represents the shared key between your site
        // and the recaptcha.
        params.add("secret", recaptchaSecret);
        // "response" is a required param and it represents the user token provided
        // by the recaptcha client-side integration on your site.
        params.add("response", captchaResponse);

        CaptchaResponse apiResponse = null;
        try {
            apiResponse = template.postForObject(recaptchaEndpoint, params, CaptchaResponse.class);
        } catch (final RestClientException e) {
            log.error("Some exception occurred while binding to the recaptcha endpoint.", e);
        }

        if (Objects.nonNull(apiResponse) && apiResponse.isSuccess()) {
            log.info("Captcha API response = {}", apiResponse.toString());
            return true;
        } else {
            return false;
        }
    }
}
