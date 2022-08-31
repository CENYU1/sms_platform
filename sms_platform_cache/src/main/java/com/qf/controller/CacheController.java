package com.qf.controller;

import com.qf.service.CacheService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * 操作redis缓存接口
 * @author cenyu
 */
@RestController
@RequestMapping("/cache")
@Slf4j
public class CacheController {
    @Autowired
    private CacheService cacheService;

    /**
     * 保存key-value到redis的String结构中
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/save2cache-persist")
    @ApiOperation(value = "保存key-value到redis的String结构中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",value = "数据的key",defaultValue = "test"),
            @ApiImplicitParam(name = "value",value = "数据的value",defaultValue = "test")
    })
    public boolean save(@RequestParam String key,@RequestParam String value){
        return cacheService.save(key,value);
    }

    /**
     * 保存key-value到redis的String结构中,传入的value是Object类型
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/save2cache-object")
    public boolean save(@RequestParam String key,@RequestParam Object value){
        return cacheService.saveObject(key,value);
    }

    @RequestMapping(value = "/get/{key}", method = RequestMethod.GET)
    public String get(@PathVariable("key") String key){
        return cacheService.get(key);
    }

    @RequestMapping(value = "/get-object/{key}", method = RequestMethod.GET)
    public Object getObject(@PathVariable("key") String key){
        return cacheService.getObject(key);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean del(@RequestParam("keys") String... keys){
        return cacheService.del(keys);
    }

    @RequestMapping(value = "/hmset/{key}", method = RequestMethod.POST)
    public boolean hMSet(@PathVariable("key") String key, @RequestBody Map map){
        return cacheService.hMSet(key,map);
    }

    @RequestMapping(value = "/hmget/{key}", method = RequestMethod.GET)
    public Map hMGet(@PathVariable("key") String key){
        return cacheService.hMGet(key);
    }

    @RequestMapping(value = "/save2cache", method = RequestMethod.POST)
    public boolean set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("expireSecond") Long expireSecond){
        return cacheService.set(key,value,expireSecond);
    }

    @RequestMapping(value = "/incr", method = RequestMethod.GET)
    public Long incr(@RequestParam("key") String key, @RequestParam("delta") long delta){
        return cacheService.incr(key,delta);
    }

    @RequestMapping(value = "/hget/{key}/{field}",method = RequestMethod.POST)
    public Object hget(@PathVariable(value = "key") String key,@PathVariable(value = "field") String field){
        return cacheService.hget(key,field);
    }

    // set结构的add操作
    @RequestMapping(value = "/sadd/{key}",method = RequestMethod.POST)
    public boolean sadd(@PathVariable(value = "key")String key,@RequestParam String... set){
        return cacheService.sadd(key,set);
    }

    // set结构的add操作
    @RequestMapping(value = "/sadd-object/{key}",method = RequestMethod.POST)
    public boolean saddObject(@PathVariable(value = "key")String key,@RequestBody Set<Map> set){
        return cacheService.saddObject(key,set);
    }

    // set结构中的members,获取set中的全部数据
    @RequestMapping(value = "/smembers/{key}",method = RequestMethod.GET)
    public Set<String> smembers(@PathVariable(value = "key") String key){
        return cacheService.smembers(key);
    }


    // zset结构的根据分数查询数据
    @RequestMapping(value = "/zrangebyscore/{key}",method = RequestMethod.GET)
    public Set<Object> zRangeByScore(@PathVariable(value = "key") String key, @RequestParam("min") Long min, @RequestParam(value = "max") Long max){
        return cacheService.zRangeByScore(key,min,max);
    }


    // 对hash结构的某一个field进行incr操作
    @PostMapping("/hincr")
    public Long hincr(@RequestParam(value = "key")String key,@RequestParam(value = "field")String field,@RequestParam(value = "delta")Long delta){
        return cacheService.hincr(key,field,delta);
    }
    
     //通配获取key集合
    @GetMapping(value = "/keys/{key}")
    public Set<String> keys(@PathVariable(value = "key")String key) {
        return cacheService.keys(key);
    }
}