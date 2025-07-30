package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.dto.CotizacionRequest;
import com.example.cotizador.cotizacion.dto.CotizacionResponse;
import reactor.core.publisher.Mono;

public interface CotizacionService {
    Mono<CotizacionResponse> cotizar(CotizacionRequest request);
}
