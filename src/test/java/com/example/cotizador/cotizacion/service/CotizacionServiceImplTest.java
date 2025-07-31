package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.dto.CotizacionRequest;
import com.example.cotizador.cotizacion.dto.CotizacionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CotizacionServiceImplTest {

    @Mock
    private ReactiveRedisTemplate<String, CotizacionResponse> redisTemplate;

    @Mock
    private ReactiveValueOperations<String, CotizacionResponse> valueOperations;

    private CotizacionServiceImpl cotizacionService;

    private static final String CACHE_KEY = "BMW|X5|2020|carga|55";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        cotizacionService = new CotizacionServiceImpl(redisTemplate);
    }

    @Test
    void testCotizar_CacheHit() {
        CotizacionResponse cachedResponse = CotizacionResponse.builder()
                .precioBase(500.0)
                .ajusteAnio(75.0)
                .ajusteUso(50.0)
                .ajusteEdad(-25.0)
                .ajusteMarca(100.0)
                .primaTotal(700.0)
                .build();

        when(valueOperations.get(CACHE_KEY)).thenReturn(Mono.just(cachedResponse));

        CotizacionRequest request = CotizacionRequest.builder()
                .marca("BMW")
                .modelo("X5")
                .anio(2020)
                .uso("carga")
                .edadConductor(55)
                .build();

        StepVerifier.create(cotizacionService.cotizar(request))
                .expectNext(cachedResponse)
                .verifyComplete();

        verify(valueOperations, times(1)).get(CACHE_KEY);
        verify(valueOperations, never()).set(anyString(), any(), any());
    }

    @Test
    void testCotizar_CacheMiss() {
        when(valueOperations.get(CACHE_KEY)).thenReturn(Mono.empty());
        when(valueOperations.set(anyString(), any(), any(Duration.class))).thenReturn(Mono.just(true));

        CotizacionRequest request = CotizacionRequest.builder()
                .marca("BMW")
                .modelo("X5")
                .anio(2020)
                .uso("carga")
                .edadConductor(55)
                .build();

        StepVerifier.create(cotizacionService.cotizar(request))
                .expectNextMatches(response -> 
                        response.getPrecioBase() == 500.0 &&
                        response.getPrimaTotal() == 700.0
                )
                .verifyComplete();

        verify(valueOperations).get(CACHE_KEY);
        verify(valueOperations).set(eq(CACHE_KEY), any(CotizacionResponse.class), eq(Duration.ofMinutes(5)));
    }
}
