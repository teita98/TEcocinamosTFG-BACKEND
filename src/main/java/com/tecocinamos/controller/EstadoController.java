package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.EstadoServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estados")
public class EstadoController {

    @Autowired
    private EstadoServiceI estadoService;

    /**
     * POST /api/v1/estados
     * Crear estado (solo ADMIN).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstadoResponseDTO> crearEstado(@Valid @RequestBody EstadoRequestDTO dto) {
        EstadoResponseDTO creado = estadoService.crearEstado(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * GET /api/v1/estados
     * Listar todos los estados (CLIENTE o ADMIN).
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<List<EstadoResponseDTO>> listarEstados() {
        List<EstadoResponseDTO> lista = estadoService.listarEstados();
        return ResponseEntity.ok(lista);
    }

    /**
     * DELETE /api/v1/estados/{id}
     * Eliminar estado (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarEstado(@PathVariable Integer id) {
        estadoService.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
