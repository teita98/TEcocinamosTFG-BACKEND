package com.tecocinamos.repository;

import com.tecocinamos.model.Estado;
import com.tecocinamos.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioId(Integer usuarioId);
    Page<Pedido> findByEstadoId(Integer estadoId, Pageable pageable);
    Page<Pedido> findAll(Pageable pageable);
}
