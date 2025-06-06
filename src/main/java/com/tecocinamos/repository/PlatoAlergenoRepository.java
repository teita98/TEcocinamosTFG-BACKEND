package com.tecocinamos.repository;

import com.tecocinamos.model.Plato;
import com.tecocinamos.model.PlatoAlergeno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatoAlergenoRepository extends JpaRepository<PlatoAlergeno, Integer> {
    void deleteByPlato(Plato plato);
    List<PlatoAlergeno> findByPlato(Plato plato);
}

