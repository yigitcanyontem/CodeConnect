package org.yigitcanyontem.cache.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.yigitcanyontem.cache.service.CacheService;

@RestController
@RequestMapping("api/v1/cache")
@AllArgsConstructor
@Slf4j
public class CacheController {
    private final CacheService cacheService;

    @PostMapping("/{key}")
    public void putValueInCache(@PathVariable("key") String key, @RequestBody Object value) {
        cacheService.putValueInCache(key, value);
    }

    @GetMapping("/{key}")
    public Object getValueFromCache(@PathVariable("key") String key) {
        return cacheService.getValueFromCache(key);
    }

    @DeleteMapping("/{key}")
    public void evictValueFromCache(@PathVariable("key") String key) {
        cacheService.evictValueFromCache(key);
    }
}
