package com.example.cotizador.cotizacion.controller;

import com.example.cotizador.cotizacion.dto.CotizacionRequest;
import com.example.cotizador.cotizacion.dto.CotizacionResponse;
import com.example.cotizador.cotizacion.service.CotizacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(CotizacionController.class)
class CotizacionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CotizacionService cotizacionService;

    private CotizacionRequest request;
    private CotizacionResponse expectedResponse;

    @BeforeEach
    void setUp() {
        request = new CotizacionRequest();
        request.setMarca("TOYOTA");
        request.setModelo("Corolla");
        request.setAnio(2020);
        request.setUso("personal");
        request.setEdadConductor(35);

        expectedResponse = CotizacionResponse.builder()
                .precioBase(500.0)
                .ajusteAnio(0.0)
                .ajusteUso(0.0)
                .ajusteEdad(0.0)
                .ajusteMarca(0.0)
                .primaTotal(500.0)
                .build();
    }

    @Test
    void testCotizarVehiculo_ReturnsCorrectResponse() {
        Mockito.when(cotizacionService.cotizar(any(CotizacionRequest.class)))
                .thenReturn(Mono.just(expectedResponse));

        webTestClient.post()
                .uri("/api/cotizaciones/cotizar")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CotizacionResponse.class)
                .value(response -> {
                    assert response.getPrimaTotal() == 500.0;
                });
    }
}
