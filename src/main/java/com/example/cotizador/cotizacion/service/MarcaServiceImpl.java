package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.dto.MarcaResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {

    private static final List<MarcaResponse> MARCAS = List.of(
        new MarcaResponse("Toyota"),
        new MarcaResponse("Hyundai"),
        new MarcaResponse("Kia"),
        new MarcaResponse("Mazda"),
        new MarcaResponse("Chevrolet"),
        new MarcaResponse("BMW"),
        new MarcaResponse("Audi")
    );

    @Override
    public Flux<MarcaResponse> listarMarcas() {
        return Flux.fromIterable(MARCAS);
    }
}
