package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.dto.CotizacionRequest;
import com.example.cotizador.cotizacion.dto.CotizacionResponse;

import java.time.Duration;


import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CotizacionServiceImpl implements CotizacionService {

    private static final double PRECIO_BASE = 500.0;
    private static final Duration CACHE_DURATION = Duration.ofMinutes(5); 


    private final Map<String, CotizacionResponse> cache = new ConcurrentHashMap<>();
    private final Map<String, Instant> cacheTimestamps = new ConcurrentHashMap<>();

    @Override
    public Mono<CotizacionResponse> cotizar(CotizacionRequest request) {
        String cacheKey = generarCacheKey(request);

        
        if (cache.containsKey(cacheKey)) {
            Instant storedTime = cacheTimestamps.get(cacheKey);
            if (storedTime != null && Instant.now().minus((TemporalAmount) CACHE_DURATION).isBefore(storedTime)) {
                
                System.out.println("Respuesta desde caché para: " + cacheKey);
                return Mono.just(cache.get(cacheKey));
            }
        }

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

        
        cache.put(cacheKey, response);
        cacheTimestamps.put(cacheKey, Instant.now());

        return Mono.just(response);
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

