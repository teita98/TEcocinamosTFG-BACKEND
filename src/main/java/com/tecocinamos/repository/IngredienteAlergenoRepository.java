package com.tecocinamos.repository;

import com.tecocinamos.model.Plato;
import com.tecocinamos.model.IngredienteAlergeno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredienteAlergenoRepository extends JpaRepository<IngredienteAlergeno, Integer> {
    List<IngredienteAlergeno> findByIngredienteId(Integer ingredienteId);
}

