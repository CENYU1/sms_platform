package com.qf.smsplatform.webmaster.service.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(value = "sms-platform-cache", fallback = CacheServiceFallback.class)
public interface CacheService {

    // 存储key-value结构,value类型为String,并且设置生存时间
    @RequestMapping(value = "/cache/save2cache-object", method = RequestMethod.POST)
    boolean saveObject(@RequestParam(value = "key") String key, @RequestParam(value = "value") Object value);

    @RequestMapping(value = "/cache/get/{key}", method = RequestMethod.GET)
    String get(@PathVariable("key") String key);
    
    @RequestMapping(value = "/cache/get-object/{key}", method = RequestMethod.GET)
    Object getObject(@PathVariable("key") String key);

    @RequestMapping(value = "/cache/delete", method = RequestMethod.POST)
    boolean del(@RequestParam("keys") String... keys);

    @RequestMapping(value = "/cache/hmset/{key}", method = RequestMethod.POST)
    boolean hMSet(@PathVariable("key") String key, @RequestBody Map map);

    @RequestMapping(value = "/cache/hmget/{key}", method = RequestMethod.GET)
    public Map hMGet(@PathVariable("key") String key);

    @RequestMapping(value = "/cache/save2cache", method = RequestMethod.POST)
    boolean set(@RequestParam("key") String key, @RequestParam("value") String value,
             @RequestParam("expireSecond") Long expireSecond);

    @RequestMapping(value = "/cache/save2cache-persist", method = RequestMethod.POST)
    boolean saveCache(@RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping(value = "/cache/incr", method = RequestMethod.GET)
    Long incr(@RequestParam("key") String key, @RequestParam("delta") long delta);

    @RequestMapping(value = "/cache/sadd")
    boolean sadd(@RequestParam("key") String key, @RequestParam("value") String... value);



}
