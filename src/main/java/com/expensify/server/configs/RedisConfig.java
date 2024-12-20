package com.expensify.server.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.expensify.server.responses.ExchangeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig{
    
    //connecting the redis server explicitly.
    @Value ("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost , redisPort);
        config.setPassword(redisPassword);
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
        .useSsl() // Enable SSL explicitly
        .build();
        return new LettuceConnectionFactory(config , clientConfig);
    }

    @Bean
    public RedisTemplate<String , ExchangeResponse> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String , ExchangeResponse> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        ObjectMapper objectMapper = new ObjectMapper();
        Jackson2JsonRedisSerializer<ExchangeResponse> serializer = new Jackson2JsonRedisSerializer<>(ExchangeResponse.class);
        serializer.setObjectMapper(objectMapper);

        template.setValueSerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }
}
