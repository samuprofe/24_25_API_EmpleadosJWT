package com.dwes.empleadosapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Crear una Entidad llamada Proyecto con los datos "nombre", "presupuesto", "descripción" y "fechaInicio".
 * Implementar una API REST para poder obtener todas, obtener una, actualizar, crear y borrar.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proyectos")
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    @Column(precision = 10, scale = 2)  //Para números de hasta 999.999.999,99 con 2 decimales
    private BigDecimal presupuesto;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaInicio;
}
