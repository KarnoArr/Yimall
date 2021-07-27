package com.zackyj.Mmall.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description Guava 的本地 Cache 工具类
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.utils ON 2021/7/26-周一.
 */
@Slf4j
public class TokenCache {
    public static final String TOKEN_PREFIX = "token_";

    //设置缓存：初始容量，最大容量（超出使用 LRU 删除）
    private static final LoadingCache<String, String> localCache =
            CacheBuilder.newBuilder()
                    .initialCapacity(1024).maximumSize(10000)
                    .expireAfterAccess(12, TimeUnit.HOURS)
                    .build(new CacheLoader<String, String>() {
                        //默认的数据加载实现,调用get取值缓存没命中时，使用该方法加载
                        @Override
                        public String load(String s) throws Exception {
                            return "null";
                        }
                    });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            log.error("localCache get error", e);
        }
        return null;
    }
}
