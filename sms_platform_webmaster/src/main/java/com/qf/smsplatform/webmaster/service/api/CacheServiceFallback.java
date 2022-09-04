package com.qf.smsplatform.webmaster.service.api;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 缓存服务的熔断
 */
@Component
public class CacheServiceFallback implements CacheService {

    @Override
    public boolean saveObject(String key, Object value) {
        return false;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public Object getObject(String key) {
        return null;
    }

    @Override
    public boolean del(String... keys) {
        return false;
    }

    @Override
    public boolean hMSet(String key, Map map) {
        return false;
    }

    @Override
    public Map hMGet(String key) {
        return null;
    }

    @Override
    public boolean set(String key, String value, Long expireSecond) {
        return false;
    }

    @Override
    public boolean saveCache(String key, String value) {
        return false;
    }

    @Override
    public Long incr(String key, long delta) {
        return null;
    }

    @Override
    public boolean sadd(String key, String... value) {
        return false;
    }
}
