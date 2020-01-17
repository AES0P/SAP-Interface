package com.aesop.demo.rfcclient.util.redis.cache;


import com.aesop.demo.rfcclient.util.redis.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * redis缓存管理类
 */
@Component
public class RedisCacheManager {

    //feedback dto cache pool
    public static final String FEEDBACK_CACHE_POOL = "feedbackCachePool";

    public static final String FEEDBACK_ALL_CACHE_POOL = "feedbackAllCachePool";


    //要生成的缓存的名字，如果在任意缓存注解中使用了下面没有定义的缓存区，会报错
    private static final String[] cachePool = {
            FEEDBACK_CACHE_POOL,    //存放单个user
            FEEDBACK_ALL_CACHE_POOL  //存放UserList
    };

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;


    @Bean
    public SimpleCacheManager simpleCacheManager() {

        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();

        Set<RedisCache> caches = new HashSet<>();

        for (String cacheName : cachePool) {
            caches.add(new RedisCache(cacheName, redisTemplate, redisUtil));
        }

        simpleCacheManager.setCaches(caches);

        return simpleCacheManager;

    }


}
