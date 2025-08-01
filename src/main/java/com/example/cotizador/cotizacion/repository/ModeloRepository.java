package com.example.cotizador.cotizacion.repository;

import com.example.cotizador.cotizacion.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    
}
