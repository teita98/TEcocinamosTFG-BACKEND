package com.tecocinamos.repository;

import com.tecocinamos.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByNombreIgnoreCase(String nombreEstado);
    boolean existsByNombreIgnoreCase(String nombreEstado);

}
