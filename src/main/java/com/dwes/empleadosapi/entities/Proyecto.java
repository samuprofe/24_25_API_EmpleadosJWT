package com.dwes.empleadosapi.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedAttribute;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "proyectos", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Empleado> empleados = new ArrayList<>();

}
