package com.example.cotizador.cotizacion.controller;

import com.example.cotizador.cotizacion.model.Modelo;
import com.example.cotizador.cotizacion.service.ModeloService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para gestionar las operaciones relacionadas con los modelos de vehículos.
 * Versión: 1.0
 * Programador: Gian Pinedo
 */
@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    /**
     * Obtiene la lista de todos los modelos disponibles.
     *
     * @return Lista de modelos
     */
    @GetMapping
    public List<Modelo> obtenerModelos() {
        return modeloService.obtenerModelos();
    }

    /**
     * Obtiene un modelo específico por su ID.
     *
     * @param id El ID del modelo a obtener
     * @return El modelo con el ID especificado, o vacío si no se encuentra
     */
    @GetMapping("/{id}")
    public Optional<Modelo> obtenerModelo(@PathVariable Long id) {
        return modeloService.obtenerModeloPorId(id);
    }

    /**
     * Crea un nuevo modelo en el sistema.
     *
     * @param modelo El modelo que se desea crear
     * @return El modelo creado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Modelo crearModelo(@RequestBody Modelo modelo) {
        return modeloService.crearModelo(modelo);
    }

    /**
     * Actualiza un modelo existente en el sistema.
     *
     * @param id El ID del modelo a actualizar
     * @param modelo Los nuevos datos del modelo
     * @return El modelo actualizado
     */
    @PutMapping("/{id}")
    public Modelo actualizarModelo(@PathVariable Long id, @RequestBody Modelo modelo) {
        return modeloService.actualizarModelo(id, modelo);
    }

    /**
     * Elimina un modelo del sistema por su ID.
     *
     * @param id El ID del modelo a eliminar
     */
    @DeleteMapping("/{id}")
    public void eliminarModelo(@PathVariable Long id) {
        modeloService.eliminarModelo(id);
    }
}
