package com.example.cotizador.cotizacion.model;

import com.example.cotizador.cotizacion.dto.CotizacionResponse;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("Cotizacion")
public class CotizacionCacheModel implements Serializable {
    private String id; // hash generado a partir del request
    private CotizacionResponse cotizacion;
}
