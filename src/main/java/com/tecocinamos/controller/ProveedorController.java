package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.ProveedorServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorServiceI proveedorService;

    /**
     * POST /api/v1/proveedores
     * Crear proveedor (solo ADMIN).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProveedorResponseDTO> crear(@Valid @RequestBody ProveedorRequestDTO dto) {
        ProveedorResponseDTO creado = proveedorService.crearProveedor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * GET /api/v1/proveedores
     * Listar proveedores (solo ADMIN).
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProveedorResponseDTO>> listarTodos() {
        List<ProveedorResponseDTO> lista = proveedorService.obtenerTodos();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/v1/proveedores/{id}
     * Obtener proveedor por ID (solo ADMIN).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Integer id) {
        ProveedorResponseDTO dto = proveedorService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT /api/v1/proveedores/{id}
     * Actualizar proveedor (solo ADMIN).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProveedorResponseDTO> actualizar(@PathVariable Integer id,
                                                           @Valid @RequestBody ProveedorRequestDTO dto) {
        ProveedorResponseDTO actualizado = proveedorService.actualizarProveedor(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * DELETE /api/v1/proveedores/{id}
     * Eliminar proveedor (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
