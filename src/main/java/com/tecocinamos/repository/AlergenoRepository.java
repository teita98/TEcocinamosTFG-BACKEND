package com.tecocinamos.repository;

import com.tecocinamos.model.Alergeno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlergenoRepository extends JpaRepository<Alergeno, Integer> {
    Optional<Alergeno> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}