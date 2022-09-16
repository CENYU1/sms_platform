package com.qf.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient("SMS-PLATFORM-CACHE")
@RequestMapping("/cache")
public interface CacheFeign {
    @RequestMapping(value = "/hmget/{key}", method = RequestMethod.GET)
    Map hMGet(@PathVariable("key") String key);
}
