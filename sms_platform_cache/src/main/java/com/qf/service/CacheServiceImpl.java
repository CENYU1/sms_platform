package com.qf.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author cenyu
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean saveObject(String key, Object value) {
        try {
            //保存数据到redis
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String get(String key) {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public Object getObject(String key) {
        log.info("【缓存模块】 获取value结构的数据,返回Object类型   key={}",key);
        Object value = redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public boolean del(String[] keys) {
        log.info("【缓存模块】 删除Redis中的key   keys={}", Arrays.toString(keys));
        redisTemplate.delete(Arrays.asList(keys));
        return true;
    }

    @Override
    public boolean hMSet(String key, Map map) {
        log.info("【缓存模块】 批量添加数据到hash结构   key={}，value={}", key,map);
        redisTemplate.opsForHash().putAll(key,map);
        return true;
    }

    @Override
    public Map hMGet(String key) {
        log.info("【缓存模块】 批量获取hash结构中的数据   key={}", key);
        Map<Object, Object> value = redisTemplate.opsForHash().entries(key);
        return value;
    }

    @Override
    public boolean set(String key, String value, Long expireSecond) {
        log.info("【缓存模块】 添加数据到Redis中，并设置生存时间   key={}，value={}，expire={}", key,value,expireSecond);
        redisTemplate.opsForValue().set(key,value,expireSecond, TimeUnit.SECONDS);
        return true;
    }

    @Override
    public Long incr(String key, long delta) {
        log.info("【缓存模块】 针对string结构实现自增   key={}，delta={}", key,delta);
        Long value = redisTemplate.opsForValue().increment(key, delta);
        return value;
    }

    @Override
    public Object hget(String key, String field) {
        log.info("【缓存模块】 根据key和field查询hash结构中的一个value   key={}，field={}", key,field);
        Object value = redisTemplate.opsForHash().get(key, field);
        return value;
    }

    @Override
    public boolean sadd(String key, String... array) {
        log.info("【缓存模块】 存储set结构数据~~   key={}，set={}", key,array);
        stringRedisTemplate.opsForSet().add(key,array);
        return true;
    }

    @Override
    public Set<String> smembers(String key) {
        log.info("【缓存模块】 获取set结构数据~~   key={}", key);
        Set<String> value = stringRedisTemplate.opsForSet().members(key);
        return value;
    }

    @Override
    public Set<Object> zRangeByScore(String key, Long min, Long max) {
        log.info("【缓存模块】根据分数范围查询Zset中的数据~~   key={}，min={}，max={}", key,min,max);
        Set<Object> value = redisTemplate.opsForZSet().rangeByScore(key, Double.parseDouble(min + ""), Double.parseDouble(max + ""));
        return value;
    }

    @Override
    public boolean saddObject(String key,Set<Map> set) {
        log.info("【缓存模块】 存储set结构Object数据~~   key={}，set={}", key,set);
        redisTemplate.opsForSet().add(key,set.toArray());
        return true;
    }

    @Override
    public Long hincr(String key, String field, Long delta) {
        log.info("【缓存模块】 对hash结构的某一个field进行incr操作   key={}，field={}，delta={}", key,field,delta);
        Long value = redisTemplate.opsForHash().increment(key, field, delta);
        return value;
    }

    @Override
    public boolean save(String key, String value) {
        log.info("【缓存模块】 添加数据 save2cache    key={},value={}",key,value);
        redisTemplate.opsForValue().set(key,value);
        return true;
    }
    
    @Override
    public Set<String> keys(String key) {
        log.info("【缓存模块】 获取keys  key={}",key);
        Set keys = redisTemplate.keys(key);
        return keys;
    }
}
