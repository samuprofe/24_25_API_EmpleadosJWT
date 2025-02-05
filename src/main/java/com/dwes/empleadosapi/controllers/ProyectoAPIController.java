package com.dwes.empleadosapi.controllers;

import com.dwes.empleadosapi.entities.Empleado;
import com.dwes.empleadosapi.entities.Proyecto;
import com.dwes.empleadosapi.repositories.EmpleadoRepository;
import com.dwes.empleadosapi.repositories.ProyectoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proyectos")
public class ProyectoAPIController {

    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;

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
    public ResponseEntity<Proyecto> createProyecto(@RequestBody @Valid Proyecto proyecto) {
        Proyecto nuevoProyecto = proyectoRepository.save(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProyecto); // HTTP 201 Created
    }

    /**
     * Actualizar un proyecto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> updateProyecto(@PathVariable Long id, @RequestBody @Valid Proyecto proyectoDetalles) {
        return proyectoRepository.findById(id)
                .map(proyecto -> {
                    proyecto.setNombre(proyectoDetalles.getNombre());
                    proyecto.setDescripcion(proyectoDetalles.getDescripcion());
                    proyecto.setPresupuesto(proyectoDetalles.getPresupuesto());
                    proyecto.setFechaInicio(proyectoDetalles.getFechaInicio());
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

    //Obtiene todos los empleados de un proyecto
    @GetMapping("/{id}/empleados")
    public ResponseEntity<List<Empleado>> getEmpleadosByProyecto(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        return proyecto.map(p -> ResponseEntity.ok(p.getEmpleados()))
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Añadir un empleado a un proyecto
     */
    @PostMapping("/{proyectoId}/empleados/{empleadoId}")
    public ResponseEntity<?> addEmpleadoToProyecto(@PathVariable Long proyectoId, @PathVariable Long empleadoId) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(empleadoId);

        if (proyectoOpt.isPresent() && empleadoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            Empleado empleado = empleadoOpt.get();

            // Evita duplicados en la lista
            if (!proyecto.getEmpleados().contains(empleado)) {
                proyecto.getEmpleados().add(empleado);
                proyectoRepository.save(proyecto);
            }
            return ResponseEntity.ok(proyecto.getEmpleados());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Eliminar un empleado de un proyecto
     */
    @DeleteMapping("/{proyectoId}/empleados/{empleadoId}")
    public ResponseEntity<?> removeEmpleadoFromProyecto(@PathVariable Long proyectoId, @PathVariable Long empleadoId) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        Optional<Empleado> empleadoOpt = empleadoRepository.findById(empleadoId);

        if (proyectoOpt.isPresent() && empleadoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            Empleado empleado = empleadoOpt.get();

            if (proyecto.getEmpleados().contains(empleado)) {
                proyecto.getEmpleados().remove(empleado);
                proyectoRepository.save(proyecto);
            }
            return ResponseEntity.ok(proyecto.getEmpleados());
        }
        return ResponseEntity.notFound().build();
    }
}
