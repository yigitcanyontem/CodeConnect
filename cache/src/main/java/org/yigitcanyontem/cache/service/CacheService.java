package org.yigitcanyontem.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheService {

    @Autowired
    private CacheManager cacheManager;

    public void putValueInCache(String key, Object value) {
        Cache cache = cacheManager.getCache("CodeConnectCache");
        if (cache != null) {
            cache.put(key, value);
            log.info("Value with key {} is put in cache", key);
        }
    }

    public Object getValueFromCache(String key) {
        Cache cache = cacheManager.getCache("CodeConnectCache");
        if (cache != null) {
            log.info("Value with key {} is retrieved from cache", key);
            return cache.get(key, Object.class);
        }
        return null;
    }

    public void evictValueFromCache(String key) {
        Cache cache = cacheManager.getCache("CodeConnectCache");
        if (cache != null) {
            cache.evict(key);
            log.info("Value with key {} is evicted from cache", key);
        }
    }
}
