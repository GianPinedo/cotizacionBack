package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.dto.CotizacionRequest;
import com.example.cotizador.cotizacion.dto.CotizacionResponse;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.data.redis.core.ReactiveRedisTemplate;


@Service
@RequiredArgsConstructor
public class CotizacionServiceImpl implements CotizacionService {

    private static final double PRECIO_BASE = 500.0;
    private static final Duration CACHE_DURATION = Duration.ofMinutes(5);

    private final ReactiveRedisTemplate<String, CotizacionResponse> redisTemplate;

    @Override
    public Mono<CotizacionResponse> cotizar(CotizacionRequest request) {
        String cacheKey = generarCacheKey(request);

        return redisTemplate.opsForValue().get(cacheKey)
            .switchIfEmpty(Mono.defer(() -> {
                double ajusteAnio = request.getAnio() > 2015 ? PRECIO_BASE * 0.15 : 0.0;
                double ajusteUso = "carga".equalsIgnoreCase(request.getUso()) ? PRECIO_BASE * 0.10 : 0.0;
                double ajusteEdad = request.getEdadConductor() > 50 ? -PRECIO_BASE * 0.05 : 0.0;
                double ajusteMarca = calcularAjusteMarca(request.getMarca());

                double primaTotal = PRECIO_BASE + ajusteAnio + ajusteUso + ajusteEdad + ajusteMarca;
                
                CotizacionResponse response = CotizacionResponse.builder()
                        .precioBase(PRECIO_BASE)
                        .ajusteAnio(ajusteAnio)
                        .ajusteUso(ajusteUso)
                        .ajusteEdad(ajusteEdad)
                        .ajusteMarca(ajusteMarca)
                        .primaTotal(primaTotal)
                        .build();

                return redisTemplate.opsForValue()
                        .set(cacheKey, response, CACHE_DURATION)
                        .thenReturn(response);
            }));
    }

    private double calcularAjusteMarca(String marca) {
        return switch (marca.toLowerCase()) {
            case "bmw" -> PRECIO_BASE * 0.20;
            case "audi" -> PRECIO_BASE * 0.10;
            default -> 0.0;
        };
    }

    private String generarCacheKey(CotizacionRequest request) {
        return request.getMarca() + "|" +
               request.getModelo() + "|" +
               request.getAnio() + "|" +
               request.getUso() + "|" +
               request.getEdadConductor();
    }
}
