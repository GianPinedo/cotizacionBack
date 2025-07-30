package com.example.cotizador.cotizacion.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CotizacionResponse {
    private double precioBase;
    private double ajusteAnio;
    private double ajusteUso;
    private double ajusteEdad;
    private double ajusteMarca;
    private double primaTotal;
}

