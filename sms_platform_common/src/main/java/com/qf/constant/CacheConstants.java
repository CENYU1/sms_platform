package com.qf.constant;

public class CacheConstants {
    // 客户信息缓存前缀
    public final static String CACHE_PREFIX_CLIENT = "CLIENT:";

    // 号段补全策略中使用的内容     20W条
    public final static String CACHE_PREFIX_PHASE = "PHASE:";

    // 黑名单信息.
    public final static String CACHE_PREFIX_BLACK = "BLACK:";

    // 客户冻结的费用
    public final static String CACHE_PREFIX_CUSTOMER_FEE = "CUSTOMER_FEE:";

    // 路由
    public final static String CACHE_PREFIX_ROUTER = "ROUTER:";

    // 敏感词
    public final static String CACHE_PREFIX_DIRTY_WORDS = "DIRTYWORDS:";

    // 使用的策略及策略顺序.
    public final static String CACHE_PREFIX_SMS_FILTERS_ORDER = "FILTERS_ORDER:";

    // 限流策略.
    public final static String CACHE_PREFIX_SMS_LIMIT = "LIMIT:";

    // 每一个客户绑定的通道信息通道信息,
    public final static String CACHE_PREFIX_SMS_CHANNEL = "CHANNEL:";           //  管道信息都存进去

    // 存放所有通道信息，用于监控模块的查询
    public final static String CACHE_PREFIX_SMS_CHANNEL_ALL = "CHANNEL:ALL";           //  管道信息都存进去
}
