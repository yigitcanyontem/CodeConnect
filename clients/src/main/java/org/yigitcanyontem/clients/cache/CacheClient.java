package org.yigitcanyontem.clients.cache;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "cache")
public interface CacheClient {
    @PostMapping("/api/v1/cache/{key}")
    void putValueInCache(@PathVariable("key") String key, @RequestBody Object value);

    @GetMapping("/api/v1/cache/{key}")
    Object getValueFromCache(@PathVariable("key") String key);

    @DeleteMapping("/api/v1/cache/{key}")
    void evictValueFromCache(@PathVariable("key") String key);
}


