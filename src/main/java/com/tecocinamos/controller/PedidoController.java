package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.PedidoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoServiceI pedidoService;

    // POST /api/v1/pedidos → Crear nuevo pedido (usuario autenticado)
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO creado = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //GET /api/v1/pedidos → listar pedidos (admin o según rol)
    @GetMapping
    public ResponseEntity<List<PedidoListDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    // GET /api/v1/pedidos/{id} → Obtener pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Integer id) {
        return ResponseEntity.ok(pedidoService.obtenerPedidoPorId(id));
    }

    // GET /api/v1/pedidos/usuario → Listar pedidos del usuario autenticado
    @GetMapping("/usuario")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidosUsuario() {
        return ResponseEntity.ok(pedidoService.listarPedidosUsuario());
    }

    // PUT /api/v1/pedidos/{id}/estado/{estadoId} → Actualizar estado del pedido (admin)
    @PutMapping("/{id}/estado/{estadoId}")
    public ResponseEntity<PedidoResponseDTO> actualizarEstado(@PathVariable Integer id,
                                                              @PathVariable Integer estadoId) {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, estadoId));
    }

    // DELETE /api/v1/pedidos/{id} → Eliminar pedido (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
