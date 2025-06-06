package com.tecocinamos.repository;

import com.tecocinamos.model.Alergeno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AlergenoRepository extends JpaRepository<Alergeno, Integer> {
}
