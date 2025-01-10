package com.dwes.empleadosapi.repositories;

import com.dwes.empleadosapi.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
}
