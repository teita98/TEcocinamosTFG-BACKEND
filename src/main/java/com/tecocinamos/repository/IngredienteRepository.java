package com.tecocinamos.repository;

import com.tecocinamos.model.Ingrediente;
import com.tecocinamos.model.PlatoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {

}
