package com.rsjava.limiting.config;


import com.rsjava.limiting.enums.Limits.Limit;
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

    @Value("${limits.IP.ttl}")
    private long ipTtl;

    @Value("${limits.NAME.ttl}")
    private long nameTtl;

    @Value("${limits.SURNAME.ttl}")
    private long surnameTtl;

    @Value("${limits.REGON.ttl}")
    private long regonTtl;

    @Bean
    public CacheManager cacheManager() {
        DefaultCacheManager defaultCacheManager = new DefaultCacheManager();
        defaultCacheManager.defineConfiguration(Limit.IP.name(), getConfiguration(ipTtl));
        defaultCacheManager.defineConfiguration(Limit.NAME.name(), getConfiguration(nameTtl));
        defaultCacheManager.defineConfiguration(Limit.SURNAME.name(), getConfiguration(surnameTtl));
        defaultCacheManager.defineConfiguration(Limit.REGON.name(), getConfiguration(regonTtl));

        return new SpringEmbeddedCacheManager(defaultCacheManager);
    }

    private org.infinispan.configuration.cache.Configuration getConfiguration(long ttl) {
        return new ConfigurationBuilder()
                .expiration().lifespan(ttl)
                .build();
    }
}