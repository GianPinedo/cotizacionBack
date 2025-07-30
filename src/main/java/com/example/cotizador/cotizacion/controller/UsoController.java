package com.example.cotizador.cotizacion.controller;

import com.example.cotizador.cotizacion.dto.UsoResponse;
import com.example.cotizador.cotizacion.service.UsoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/uso")
@RequiredArgsConstructor
public class UsoController {

    private final UsoService usoService;

    @GetMapping
    public Flux<UsoResponse> listarUsos() {
        return usoService.listarUsos();
    }
}
