package com.tecocinamos.service;

import com.tecocinamos.dto.*;

import java.util.List;

public interface PedidoServiceI {

    PedidoResponseDTO crearPedido(PedidoRequestDTO dto);

    PedidoResponseDTO obtenerPedidoPorId(Integer id);

    List<PedidoListDTO> listarPedidos(); // Admin

    List<PedidoResponseDTO> listarPedidosUsuario(); // Usuario autenticado

    PedidoResponseDTO actualizarEstado(Integer pedidoId, Integer nuevoEstadoId);

    void eliminarPedido(Integer id);
}
