package com.ypf.config;

import com.ypf.entity.userEntity;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
@Configuration
public class cacheConfigurer {
    @Bean("cacheManager")
    @Primary
    public CacheManager cacheManager  (RedisConnectionFactory factory){
        RedisCacheConfiguration cacheConfiguration =RedisCacheConfiguration
                .defaultCacheConfig().entryTtl(Duration.ofSeconds(10))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.
                        SerializationPair
                        .fromSerializer(new StringRedisSerializer())) //定义了key和value的序列化协议，同时hash key和hash value也被定义
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new Jackson2JsonRedisSerializer<>(getClass())));
        System.out.println("使用了该cachemanager");
        return RedisCacheManager.builder(factory)
                .cacheDefaults(cacheConfiguration)
                .build();

    }
}
