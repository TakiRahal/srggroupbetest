package com.takirahal.srfgroup.config;

import com.takirahal.srfgroup.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsCustomConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedMethod("GET");     //get
        config.addAllowedMethod("POST");    //post
        config.addAllowedMethod("PUT");     //put
        config.addAllowedMethod("PATCH");   //patch
        config.addAllowedMethod("DELETE");  //delete

        // Authorize origin
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        // Autorize custom header
        config.addExposedHeader(JwtAuthenticationFilter.AUTHORIZATION_HEADER);
        config.addExposedHeader("X-app-alert");

        UrlBasedCorsConfigurationSource corsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/api/**", config);
        // corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }
}
