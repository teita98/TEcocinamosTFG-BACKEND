package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.RolServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RolController {

    @Autowired
    private RolServiceI rolService;

    /**
     * POST /api/v1/roles
     * Crear rol (solo ADMIN).
     */
    @PostMapping
    public ResponseEntity<RolResponseDTO> crearRol(@Valid @RequestBody RolRequestDTO dto) {
        RolResponseDTO creado = rolService.crearRol(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * GET /api/v1/roles
     * Listar roles (solo ADMIN).
     */
    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listarRoles() {
        List<RolResponseDTO> lista = rolService.listarRoles();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/v1/roles/{id}
     * Obtener rol por ID (solo ADMIN).
     */
    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerPorId(@PathVariable Integer id) {
        RolResponseDTO dto = rolService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * DELETE /api/v1/roles/{id}
     * Eliminar rol (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}
