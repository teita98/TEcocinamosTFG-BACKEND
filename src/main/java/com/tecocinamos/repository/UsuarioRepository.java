package com.tecocinamos.repository;

import com.tecocinamos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    // Buscar usuarios activos cuyo nombre empiece por 'nombre' (ignorando mayúsculas)
    List<Usuario> findByNombreStartingWithIgnoreCaseAndEliminadoFalse(String nombre);

    // Buscar usuario activo exacto por nombre
    Optional<Usuario> findByNombreIgnoreCaseAndEliminadoFalse(String nombre);

    // Buscar usuario activo por ID
    Optional<Usuario> findByIdAndEliminadoFalse(Integer id);
}
