package com.tecocinamos.repository;

import com.tecocinamos.model.DetallesPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallesPedidoRepository extends JpaRepository<DetallesPedido, Integer> {
}
