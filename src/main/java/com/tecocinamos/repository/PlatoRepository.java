package com.tecocinamos.repository;

import com.tecocinamos.model.Plato;
import com.tecocinamos.model.PlatoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {

    // Buscar por nombre exacto o parcial
    List<Plato> findByNombrePlatoContainingIgnoreCase(String nombrePlato);

    // Listar por categoría
    List<Plato> findByCategoriaNombreIgnoreCase(String nombreCategoria);


}
