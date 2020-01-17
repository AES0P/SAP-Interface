package com.aesop.demo.rfcclient.util.redis.cache;

import com.aesop.demo.rfcclient.util.redis.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Callable;

/**
 * redis cache 实现
 */
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class RedisCache implements Cache {

    private final long liveTime = 86400;//缓存存活时间

    private String name;

    private RedisTemplate<String, Object> redisTemplate;

    private RedisUtil redisUtil;//这里要用到自定义的序列化工具

    @Override
    public void clear() {
        log.info("\r\n-------緩存清理------");
        redisTemplate.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "ok";
        });
    }

    @Override
    public void evict(Object key) {
        log.info("\r\n-------緩存刪除------");
        final String keyf = key.toString();
        redisTemplate.execute((RedisCallback<Long>) connection -> connection.del(keyf.getBytes()));

    }


    @Override
    public ValueWrapper get(Object key) {

        log.info("\r\n" + "------缓存获取-------" + key.toString());
        final String keyf = key.toString();
        Object object = null;
        object = redisTemplate.execute((RedisCallback<Object>) connection -> {
            byte[] key1 = keyf.getBytes();
            byte[] value = connection.get(key1);
            if (value == null) {
                log.info("\r\n" + "------缓存不存在-------");
                return null;
            }
//                return SerializationUtils.deserialize(value);
            return redisUtil.toObject(value);//自定义的反序列化方式
        });
        ValueWrapper obj = (object != null ? new SimpleValueWrapper(object) : null);
        log.info("\r\n" + "------获取到内容-------" + obj);
        return obj;
    }

    @Override
    public void put(Object key, Object value) {
        log.info("\r\n" + "-------加入缓存------");
        log.info("\r\n" + "key----:" + key);
        log.info("\r\n" + "value----:" + value);
        final String keyString = key.toString();
        final Object valuef = value;
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            byte[] keyb = keyString.getBytes();
//                byte[] valueb = SerializationUtils.serialize((Serializable) valuef);
            byte[] valueb = redisUtil.toByteArray(valuef);//自定义的序列化方式
            connection.set(keyb, valueb);
            if (liveTime > 0) {
                connection.expire(keyb, liveTime);
            }
            return 1L;
        });

    }


    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper putIfAbsent(Object arg0, Object arg1) {
        return null;
    }

    @Override
    public <T> T get(Object arg0, Class<T> arg1) {
        return null;
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

}
