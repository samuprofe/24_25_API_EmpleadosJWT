package com.dwes.empleadosapi.controllers;

import com.dwes.empleadosapi.entities.Empleado;
import com.dwes.empleadosapi.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmpleadoAPIController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Obtener todos los empleados en un JSON
     */
    @GetMapping("/empleados")
    public List<Empleado> getListEmpleados(){
        return empleadoRepository.findAll();
    }

    /**
     * Insertar un empleado (recibe los datos en el cuerpo (body) en formato JSON)
     */
    @PostMapping("/empleados")
    public Empleado insertEmpleado(@RequestBody Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    /**
     * Obtiene un empleado
     */
    @GetMapping("/empleados/{id}")
    public Empleado getEmpleado(@PathVariable Long id){
        return empleadoRepository.findById(id).orElse(null);
    }

    /**
     * Modifica un empleado
     */
    @PutMapping("/empleados/{id}")
    public Empleado editEmpleado(@PathVariable Long id, @RequestBody Empleado nuevoEmpleado){
        /*
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        if(empleado.isPresent()){
            empleado.get().setNombre(nuevoEmpleado.getNombre());
            empleado.get().setEmail(nuevoEmpleado.getEmail());
            empleado.get().setApellidos(nuevoEmpleado.getApellidos());
            return empleadoRepository.save(empleado.get());
        }else{
            return empleadoRepository.save(nuevoEmpleado);
        }
        */
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(nuevoEmpleado.getNombre());
                    empleado.setApellidos(nuevoEmpleado.getApellidos());
                    empleado.setEmail(nuevoEmpleado.getEmail());
                    return empleadoRepository.save(empleado);
                })
                .orElseGet(() -> {
                    return empleadoRepository.save(nuevoEmpleado);
                });
    }

    /**
     * Borra un empleado
     */
    @DeleteMapping("/empleados/{id}")
    public Empleado deleteEmpleado(@PathVariable Long id){
        return null;
    }
}
