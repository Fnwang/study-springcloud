package com.b2c.es.config;

import com.b2c.es.model.MemberModel;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.net.UnknownHostException;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 自定义redis加载器
 */
@Configuration
public class MyRedisConfig {

    /**
     * 创建自定义的缓存格式
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public RedisTemplate<Object, MemberModel> myRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)throws UnknownHostException
    {
        RedisTemplate<Object, MemberModel> template = new RedisTemplate<Object, MemberModel>();

        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<MemberModel> jsrs = new Jackson2JsonRedisSerializer(MemberModel.class);

        template.setDefaultSerializer(jsrs);

        return template;
    }


    /**
     * 自定义缓存管理器。
     * @param connectionFactory
     * @return
     */
    @Bean(name = "myCacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        Jackson2JsonRedisSerializer<MemberModel> redisSerializer = new Jackson2JsonRedisSerializer<>(MemberModel.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        redisSerializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer));

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfiguration).build();

        return redisCacheManager;
    }
}
