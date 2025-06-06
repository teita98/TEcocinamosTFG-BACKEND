package com.tecocinamos.repository;

import com.tecocinamos.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombreRolIgnoreCase(String nombreRol);
    boolean existsByNombreRolIgnoreCase(String nombreRol);

}
