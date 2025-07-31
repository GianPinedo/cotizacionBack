package com.example.cotizador.cotizacion.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CotizacionRequest {

    @NotBlank
    private String marca;

    @NotBlank
    private String modelo;

    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer anio;

    @NotBlank
    private String uso; 

    @NotNull
    @Min(18)
    @Max(100)
    private Integer edadConductor;
}
