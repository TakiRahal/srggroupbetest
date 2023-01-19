package com.takirahal.srfgroup.config;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching()
@ConditionalOnProperty(
        value="redis.available",
        havingValue = "true",
        matchIfMissing = false)
public class CacheConfiguration {


}
