package com.tecocinamos.repository;

import com.tecocinamos.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    Page<Ingrediente> findAll(Pageable pageable);
    Page<Ingrediente> findByProveedorId(Integer proveedorId, Pageable pageable);

}
