package com.rsjava.limiting.config;


import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.spring.embedded.provider.SpringEmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching

public class CacheConfig {

    @Value("${spring.cache.infinispan.caches.ip.ttl}")
    private long ipTtl;

    @Value("${spring.cache.infinispan.caches.name.ttl}")
    private long nameTtl;

    @Value("${spring.cache.infinispan.caches.surname.ttl}")
    private long surnameTtl;

    @Value("${spring.cache.infinispan.caches.regon.ttl}")
    private long regonTtl;

    @Bean
    public CacheManager cacheManager() {
        DefaultCacheManager defaultCacheManager = new DefaultCacheManager();

        org.infinispan.configuration.cache.Configuration ipCacheConfig = new ConfigurationBuilder()
                .expiration().lifespan(ipTtl)
                .build();

        org.infinispan.configuration.cache.Configuration nameCacheConfig = new ConfigurationBuilder()
                .expiration().lifespan(nameTtl)
                .build();

        org.infinispan.configuration.cache.Configuration surnameCacheConfig = new ConfigurationBuilder()
                .expiration().lifespan(surnameTtl)
                .build();

        org.infinispan.configuration.cache.Configuration regonCacheConfig = new ConfigurationBuilder()
                .expiration().lifespan(regonTtl)
                .build();

        defaultCacheManager.defineConfiguration("id", ipCacheConfig);
        defaultCacheManager.defineConfiguration("name", nameCacheConfig);
        defaultCacheManager.defineConfiguration("surname", surnameCacheConfig);
        defaultCacheManager.defineConfiguration("regon", regonCacheConfig);

        return new SpringEmbeddedCacheManager(defaultCacheManager);
    }
}