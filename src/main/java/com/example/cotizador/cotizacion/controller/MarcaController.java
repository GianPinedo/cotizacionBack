package com.example.cotizador.cotizacion.controller;

import com.example.cotizador.cotizacion.dto.MarcaResponse;
import com.example.cotizador.cotizacion.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/marca")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    @Operation(summary = "Lista las marcas disponibles")
    public Flux<MarcaResponse> listarMarcas() {
        return marcaService.listarMarcas();
    }
}
