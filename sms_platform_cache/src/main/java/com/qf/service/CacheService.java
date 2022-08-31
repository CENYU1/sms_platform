package com.qf.service;

import java.util.Map;
import java.util.Set;

/**
 * @author cenyu
 */
public interface CacheService {
    /**
     * 存储key, value结构到redis中
     */
    public boolean saveObject(String key, Object value);

    /**
     * 根据key到redis中获取value值
     */
    public String get(String key);

    public Object getObject(String key);

    public boolean del(String[] keys);

    public boolean hMSet(String key, Map map);

    public Map hMGet(String key);

    public boolean set(String key, String value, Long expireSecond);

    public Long incr(String key, long delta);

    /**
     * 根据key和field查询hash结构中的一个value
     * @param key
     * @param field
     * @return
     */
    public Object hget(String key, String field);

    /**
     * 向set结构添加数据
     * @param key
     * @param set
     * @return
     */
    public boolean sadd(String key, String... set);

    /**
     * 查询set结构中的全部数据
     * @param key
     * @return
     */
    public Set<String> smembers(String key);

    /**
     * zset结构的根据分数查询数据
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<Object> zRangeByScore(String key, Long min, Long max);

    /**
     * 向set结构添加Object数据
     * @param key
     * @param set
     * @return
     */
    public boolean saddObject(String key, Set<Map> set);

    /**
     * 对hash结构的某一个field进行incr操作
     * @param key
     * @param field
     * @param delta
     * @return
     */
    public Long hincr(String key, String field, Long delta);

    /**
     * 保存key-value到redis的String结构中
     * @param key
     * @param value
     * @return
     */
    boolean save(String key, String value);
    
    /**
     * 获取key
     * @param key
     * @return
     */
    public Set<String> keys(String key);
}
