package com.rsjava.limiting.service;

import com.rsjava.limiting.enums.Limits.Limit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class LimitService {

    @Value("${limits.ip.attempts}")
    private int ipLimit;

    @Value("${limits.name.attempts}")
    private int nameLimit;

    @Value("${limits.surname.attempts}")
    private int surnameLimit;

    @Value("${limits.regon.attempts}")
    private int regonLimit;

    private final CacheManager cacheManager;

    public LimitService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void checkIpLimit(String id) {
        checkLimit(id, Limit.IP.name(), ipLimit);
    }

    public void checkNameLimit(String name) {
        checkLimit(name, Limit.NAME.name(), nameLimit);
    }

    public void checkSurnameLimit(String surname) {
        checkLimit(surname, Limit.SURNAME.name(), surnameLimit);
    }

    public void checkRegonLimit(String regon) {
        checkLimit(regon, Limit.REGON.name(), regonLimit);
    }

    private void checkLimit(String key, String cacheName, int limit) {
        Cache cache = cacheManager.getCache(cacheName);
        Integer currentCount = cache.get(key, Integer.class);

        if (currentCount == null) {
            cache.put(key, 1);
        } else {
            if (currentCount >= limit) {
                throw new RuntimeException("Max amount of attempts exceeded: " + cacheName + " " + key);
            }
            cache.put(key, currentCount + 1);
        }
    }
}