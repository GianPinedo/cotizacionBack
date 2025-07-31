package com.example.cotizador.config;

import com.example.cotizador.cotizacion.dto.CotizacionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, CotizacionResponse> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory,
            ObjectMapper objectMapper) {

        RedisSerializer<String> keySerializer = new StringRedisSerializer();

        
        Jackson2JsonRedisSerializer<CotizacionResponse> valueSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, CotizacionResponse.class);

        RedisSerializationContext<String, CotizacionResponse> context =
                RedisSerializationContext.<String, CotizacionResponse>newSerializationContext(keySerializer)
                        .value(valueSerializer)
                        .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
