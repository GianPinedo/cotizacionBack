package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.dto.UsoResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class UsoService {

    public Flux<UsoResponse> listarUsos() {
        return Flux.fromIterable(List.of(
            new UsoResponse("personal", "Personal"),
            new UsoResponse("trabajo", "Trabajo"),
            new UsoResponse("carga", "Carga")
        ));
    }
}
