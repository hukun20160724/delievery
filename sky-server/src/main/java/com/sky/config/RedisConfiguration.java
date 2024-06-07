package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * ClassName: RedisConfiguration
 * Package: com.sky.config
 * Description:
 *
 * @Author Kun Hu
 * @Create 6/7/24 16:13
 * @Version 1.0
 */
@Configuration
@Slf4j
public class RedisConfiguration {


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info(" start the redis template .........");
        // new object
        RedisTemplate redisTemplate = new RedisTemplate();
        //set redisConnectionFactory object
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //set redis key serilazion
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;

    }
}
