package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.dto.MarcaResponse;
import reactor.core.publisher.Flux;

public interface MarcaService {
    Flux<MarcaResponse> listarMarcas();
}
