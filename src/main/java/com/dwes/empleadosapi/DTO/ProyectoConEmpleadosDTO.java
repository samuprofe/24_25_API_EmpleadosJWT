package com.dwes.empleadosapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class ProyectoConEmpleadosDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private Integer numeroDeEmpleados;
}
