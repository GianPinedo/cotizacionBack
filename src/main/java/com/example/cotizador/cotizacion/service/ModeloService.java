package com.example.cotizador.cotizacion.service;

import com.example.cotizador.cotizacion.model.Modelo;
import com.example.cotizador.cotizacion.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModeloService {

    private final ModeloRepository modeloRepository;

    
    public Modelo crearModelo(Modelo modelo) {
        return modeloRepository.save(modelo);
    }

  
    public List<Modelo> obtenerModelos() {
        return modeloRepository.findAll();
    }

  
    public Optional<Modelo> obtenerModeloPorId(Long id) {
        return modeloRepository.findById(id);
    }

   
    public Modelo actualizarModelo(Long id, Modelo modeloActualizado) {
        return modeloRepository.findById(id)
                .map(modelo -> {
                    modelo.setNombre(modeloActualizado.getNombre());
                    // Actualiza otros campos aquí si es necesario
                    return modeloRepository.save(modelo);
                }).orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
    }

   
    public void eliminarModelo(Long id) {
        modeloRepository.deleteById(id);
    }
}
