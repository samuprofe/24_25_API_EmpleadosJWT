package com.dwes.empleadosapi.controllers;

import com.dwes.empleadosapi.entities.Proyecto;
import com.dwes.empleadosapi.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proyectos")
public class ProyectoAPIController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    /**
     * Obtener todos los proyectos
     */
    @GetMapping
    public ResponseEntity<List<Proyecto>> getProyectos() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        return ResponseEntity.ok(proyectos); // HTTP 200 OK
    }

    /**
     * Obtener un proyecto por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> getProyectoById(@PathVariable Long id) {
        return proyectoRepository.findById(id)
                .map(proyecto -> ResponseEntity.ok(proyecto)) // HTTP 200 OK
                .orElse(ResponseEntity.notFound().build());   // HTTP 404 Not Found
    }

    /**
     * Crear un nuevo proyecto
     */
    @PostMapping
    public ResponseEntity<Proyecto> createProyecto(@RequestBody Proyecto proyecto) {
        Proyecto nuevoProyecto = proyectoRepository.save(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProyecto); // HTTP 201 Created
    }

    /**
     * Actualizar un proyecto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoDetalles) {
        return proyectoRepository.findById(id)
                .map(proyecto -> {
                    if (proyectoDetalles.getNombre() != null) {
                        proyecto.setNombre(proyectoDetalles.getNombre());
                    }
                    if (proyectoDetalles.getDescripcion() != null) {
                        proyecto.setDescripcion(proyectoDetalles.getDescripcion());
                    }
                    if (proyectoDetalles.getPresupuesto() != null) {
                        proyecto.setPresupuesto(proyectoDetalles.getPresupuesto());
                    }
                    if (proyectoDetalles.getFechaInicio() != null) {
                        proyecto.setFechaInicio(proyectoDetalles.getFechaInicio());
                    }
                    Proyecto proyectoActualizado = proyectoRepository.save(proyecto);
                    return ResponseEntity.ok(proyectoActualizado); // HTTP 200 OK
                })
                .orElse(ResponseEntity.notFound().build()); // HTTP 404 Not Found
    }

    /**
     * Borrar un proyecto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProyecto(@PathVariable Long id) {
        return proyectoRepository.findById(id)
                .map(proyecto -> {
                    proyectoRepository.delete(proyecto);
                    return ResponseEntity.noContent().build(); // HTTP 204 No Content
                })
                .orElse(ResponseEntity.notFound().build()); // HTTP 404 Not Found
    }
}
