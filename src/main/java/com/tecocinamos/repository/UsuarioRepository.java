package com.tecocinamos.repository;

import com.tecocinamos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByNombreStartingWithIgnoreCaseAndEliminadoFalse(String nombre);
    Optional<Usuario> findByNombreIgnoreCaseAndEliminadoFalse(String nombre);
    Optional<Usuario> findByIdAndEliminadoFalse(Integer id);
    boolean existsByEmail(String email);
}
