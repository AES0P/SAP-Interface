package com.aesop.demo.rfcclient.util.redis.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

/**
 * redis配置类 自定义redistemplate的序列化方式
 *
 * @Date: 2019年12月10日15:39:19
 * @Author: Aesop
 * @Description: springboot中，自动注入了RedisConnectionFactory、StringRedisTemplate或RedisTemplate，
 * 但是一般不符合我们的实际需求，需要对redis提供的默认Template进行序列化配置，这也是官方文档提出的方法，用自定义的覆盖默认的
 */
@Configuration
@EnableCaching
@Getter
@Slf4j
public class RedisTemplateConfig extends CachingConfigurerSupport {

    //springboot默认配置的 RedisConnectionFactory
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    //springboot默认配置的 RedisTemplate
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    //springboot默认配置的 StringRedisTemplate
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Setter
    private Duration timeToLive = Duration.ofHours(1);


    /**
     * 自定义redistemplate
     *
     * @return
     */
    @Bean(value = "redisTemp")//名字不能和springboot提供的默认模板相同
    @SuppressWarnings({"rawtypes", "unchecked"})
    public RedisTemplate<String, Object> redisTemplate() {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key-value结构序列化数据结构
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        // hash数据结构序列化方式,必须这样否则存hash 就是基于jdk序列化的
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 启用默认序列化方式
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

        // redisTemplate.afterPropertiesSet();
        log.info("生成自定义 redisTemplate");
        return redisTemplate;
    }

    @Bean(value = "stringRedisTemp")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        log.info("生成自定义 stringRedisTemplate");
        return stringRedisTemplate;
    }

    /**
     * 自定义缓存key的生成类实现
     */
    @Bean(name = "myKeyGenerator")
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... params) {
                log.info("自定义缓存，使用第一参数作为缓存key，params = " + Arrays.toString(params));
                // 仅仅用于测试，实际不可能这么写
                return params[0];
            }
        };
    }

//    /**
//     * 缓存管理用simplecache，这里就不启用了
//     * 申明缓存管理器，会创建一个切面（aspect）并触发Spring缓存注解的切点（pointcut）
//     * 根据类或者方法所使用的注解以及缓存的状态，这个切面会从缓存中获取数据，将数据添加到缓存之中或者从缓存中移除某个值
//     *
//     * @return
//     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//
////        return RedisCacheManager.create(connectionFactory);
//
//        RedisCacheConfiguration redisCacheConfiguration =
//                RedisCacheConfiguration.defaultCacheConfig().entryTtl(this.timeToLive)// 设置缓存有效期
////                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
////                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
//                        .disableCachingNullValues();
//
//        log.info("生成 CacheManager");
//        return RedisCacheManager.builder(
//                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
//                .cacheDefaults(redisCacheConfiguration)
//                .transactionAware()
//                .build();
//
//
//    }

//
//    /**
//     * 对hash类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForHash();
//    }
//
//    /**
//     * 对redis字符串类型数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForValue();
//    }
//
//    /**
//     * 对链表类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForList();
//    }
//
//    /**
//     * 对无序集合类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForSet();
//    }
//
//    /**
//     * 对有序集合类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForZSet();
//    }


}
