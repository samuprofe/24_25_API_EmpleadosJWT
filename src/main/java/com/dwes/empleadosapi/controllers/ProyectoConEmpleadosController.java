package com.dwes.empleadosapi.controllers;

import com.dwes.empleadosapi.DTO.ProyectoConEmpleadosDTO;
import com.dwes.empleadosapi.entities.Proyecto;
import com.dwes.empleadosapi.repositories.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProyectoConEmpleadosController {

    @Autowired
    ProyectoRepository proyectoRepository;

    @GetMapping("/proyectos_con_empleados")
    public ResponseEntity<List<ProyectoConEmpleadosDTO>> getProyectoConEmpleados(){
        List<ProyectoConEmpleadosDTO> proyectosDTO = new ArrayList<>();

        proyectoRepository.findAll().forEach(proyecto -> {
            proyectosDTO.add(
                    ProyectoConEmpleadosDTO.builder()
                            .nombre(proyecto.getNombre())
                            .descripcion(proyecto.getDescripcion())
                            .fechaInicio(proyecto.getFechaInicio())
                            .numeroDeEmpleados(proyecto.getEmpleados().size())
                            .build()
            );
        });

        /*
        List<Proyecto> proyectos = proyectoRepository.findAll();
        proyectos.forEach(proyecto -> {
            ProyectoConEmpleadosDTO proyectoDTO = new ProyectoConEmpleadosDTO(
                    proyecto.getNombre(),
                    proyecto.getDescripcion(),
                    proyecto.getFechaInicio(),
                    proyecto.getEmpleados().size()
            );
            proyectosDTO.add(proyectoDTO);
        })
        */
        return ResponseEntity.ok(proyectosDTO);
    }

}
