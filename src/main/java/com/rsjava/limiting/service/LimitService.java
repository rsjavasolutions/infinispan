package com.rsjava.limiting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class LimitService {

    @Value("${spring.cache.infinispan.limits.ip}")
    private int ipLimit;

    @Value("${spring.cache.infinispan.limits.name}")
    private int nameLimit;

    @Value("${spring.cache.infinispan.limits.surname}")
    private int surnameLimit;

    @Value("${spring.cache.infinispan.limits.regon}")
    private int regonLimit;

    private static final String IP_CACHE = "ip";
    private static final String NAME_CACHE = "name";
    private static final String SURNAME_CACHE = "surname";
    private static final String REGON_CACHE = "regon";

    private final CacheManager cacheManager;

    public LimitService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    // Metoda do sprawdzania limitu dla IP
    public void checkIpLimit(String id) {
        checkLimit(id, IP_CACHE, ipLimit);
    }

    // Metoda do sprawdzania limitu dla NAME
    public void checkNameLimit(String name) {
        checkLimit(name, NAME_CACHE, nameLimit);
    }

    // Metoda do sprawdzania limitu dla SURNAME
    public void checkSurnameLimit(String surname) {
        checkLimit(surname, SURNAME_CACHE, surnameLimit);
    }

    // Metoda do sprawdzania limitu dla REGON
    public void checkRegonLimit(String regon) {
        checkLimit(regon, REGON_CACHE, regonLimit);
    }

    // Ogólna metoda sprawdzająca limit dla danego cache'a i klucza
    private void checkLimit(String key, String cacheName, int limit) {
        // Uzyskujemy dostęp do odpowiedniego cache'a za pomocą CacheManager
        Cache cache = cacheManager.getCache(cacheName);
        System.out.println("Cache : " + cache);

        // Sprawdzamy bieżącą liczbę prób
        Integer currentCount = cache.get(key, Integer.class);
        System.out.println("Current cout wynosi " + currentCount);
        System.out.println("Liczba wywołań dla " + key + " wynosi " + currentCount);

        if (currentCount == null) {
            // Jeśli to pierwsze wywołanie, ustawiamy licznik na 1
            cache.put(key, 1);
        } else {
            // Jeśli liczba prób przekroczyła dozwolony limit, rzucamy wyjątek
            if (currentCount >= limit) {
                throw new RuntimeException("Przekroczono dozwoloną liczbę prób dla: " + key);
            }
            // Inkrementujemy licznik prób
            cache.put(key, currentCount + 1);
        }
    }
}