package com.tecocinamos.service;

import com.tecocinamos.dto.PedidoListDTO;
import com.tecocinamos.dto.PedidoRequestDTO;
import com.tecocinamos.dto.PedidoResponseDTO;
import org.springframework.data.domain.*;

import java.util.List;

public interface PedidoServiceI {
    PedidoResponseDTO crearPedido(PedidoRequestDTO dto);
    PedidoResponseDTO obtenerPedidoPorId(Integer id);
    List<PedidoListDTO> listarPedidosUsuario();
    Page<PedidoListDTO> listarTodosPedidos(Integer estadoId, Pageable pageable);
    PedidoResponseDTO actualizarEstado(Integer pedidoId, Integer nuevoEstadoId);
    void eliminarPedido(Integer id);
}
