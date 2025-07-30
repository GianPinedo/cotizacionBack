package com.example.cotizador.cotizacion.controller;

import com.example.cotizador.cotizacion.dto.MarcaResponse;
import com.example.cotizador.cotizacion.service.MarcaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@WebFluxTest(MarcaController.class)
class MarcaControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MarcaService marcaService;

    private List<MarcaResponse> listaMarcas;

    @BeforeEach
    void setUp() {
        listaMarcas = List.of(
                new MarcaResponse("TOYOTA"),
                new MarcaResponse("KIA"),
                new MarcaResponse("BMW")
        );
    }

    @Test
    void testListarMarcas_ReturnsMarcaList() {
        Mockito.when(marcaService.listarMarcas())
                .thenReturn(Flux.fromIterable(listaMarcas));

        webTestClient.get()
                .uri("/api/modelos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MarcaResponse.class)
                .hasSize(3)
                .value(marcas -> {
                    assert marcas.get(0).getNombre().equals("TOYOTA");
                    assert marcas.get(1).getNombre().equals("KIA");
                    assert marcas.get(2).getNombre().equals("BMW");
                });
    }
}
