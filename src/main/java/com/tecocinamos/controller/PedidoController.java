package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.PedidoServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoServiceI pedidoService;

    /**
     * POST /api/v1/pedidos
     * Crear nuevo pedido (requiere autenticación). Rol CLIENTE o ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        PedidoResponseDTO creado = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * GET /api/v1/pedidos/usuario  → Listar pedidos del usuario autenticado (detalle completo).
     * Listar pedidos del usuario autenticado (CLIENTE).
     */
    @GetMapping("/usuario")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<List<PedidoListDTO>> listarPedidosUsuario() {
        List<PedidoListDTO> lista = pedidoService.listarPedidosUsuario();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/v1/pedidos?page=&size=&estadoId=
     * Listar todos los pedidos (solo ADMIN) con paginación y filtro por estado opcional.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<PedidoListDTO>> listarPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Integer estadoId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaCreado").descending());
        Page<PedidoListDTO> dtoPage = pedidoService.listarTodosPedidos(estadoId, pageable);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * GET /api/v1/pedidos/{id}
     * Obtener pedido por ID (CLIENTE ve sólo sus pedidos; ADMIN ve cualquiera).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Integer id) {
        PedidoResponseDTO dto = pedidoService.obtenerPedidoPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/v1/pedidos/{id}
     * Cancelar pedido (CLIENTE si está pendiente) o eliminar (ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * PUT /api/v1/pedidos/{id}/estado/{estadoId}
     * Actualizar estado del pedido (solo ADMIN).
     */
    @PutMapping("/{id}/estado/{estadoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PedidoResponseDTO> actualizarEstado(
            @PathVariable Integer id,
            @PathVariable Integer estadoId) {
        PedidoResponseDTO actualizado = pedidoService.actualizarEstado(id, estadoId);
        return ResponseEntity.ok(actualizado);
    }
}
