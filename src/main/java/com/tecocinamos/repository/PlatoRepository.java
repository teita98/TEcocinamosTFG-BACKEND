package com.tecocinamos.repository;

import com.tecocinamos.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    Page<Plato> findAll(Pageable pageable);
    Page<Plato> findByCategoriaNombreIgnoreCase(String categoria, Pageable pageable);
    Page<Plato> findByNombrePlatoContainingIgnoreCase(String nombre, Pageable pageable);
    List<Plato> findByCategoriaNombreIgnoreCase(String categoria);
    List<Plato> findByNombrePlatoContainingIgnoreCase(String nombre);
}
