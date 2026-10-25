package com.example.DifferentWorlds.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfiguration {

    private static final int MAX_CACHE_ENTRIES = 10_000;

    @Bean
    public CacheManager cacheManager(@Value("${security.jwt.expiration-time}") long tokenLifetimeMs) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(tokenLifetimeMs, TimeUnit.MILLISECONDS)
                .maximumSize(MAX_CACHE_ENTRIES));
        return cacheManager;
    }
}