package com.tecocinamos.repository;

import com.tecocinamos.model.Plato;
import com.tecocinamos.model.PlatoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatoIngredienteRepository extends JpaRepository<PlatoIngrediente, Integer> {
    List<PlatoIngrediente> findByPlato(Plato plato);
    void deleteByPlato(Plato plato);

}
