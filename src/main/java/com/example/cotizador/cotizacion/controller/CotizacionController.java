package com.example.cotizador.cotizacion.controller;

import com.example.cotizador.cotizacion.dto.CotizacionRequest;
import com.example.cotizador.cotizacion.dto.CotizacionResponse;
import com.example.cotizador.cotizacion.service.CotizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cotizaciones")
@RequiredArgsConstructor
@Tag(name = "Cotizador API", description = "Cotiza seguros vehiculares")
public class CotizacionController {

    private final CotizacionService cotizacionService;

    @PostMapping(value = "/cotizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Calcular cotización", description = "Retorna precio base, ajustes y prima total para un vehículo")
    public Mono<CotizacionResponse> cotizarVehiculo(@Valid @RequestBody CotizacionRequest request) {
        return cotizacionService.cotizar(request);
    }
}
