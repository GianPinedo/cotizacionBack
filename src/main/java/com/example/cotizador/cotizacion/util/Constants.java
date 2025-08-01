package com.example.cotizador.cotizacion.util;

import java.time.Duration;

public final class Constants {
    
    public static final double PRECIO_BASE = 500.0;
    public static final Duration CACHE_DURATION = Duration.ofMinutes(5);

    private Constants() {
        // Prevenir instanciación
    }
}
