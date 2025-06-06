package com.tecocinamos.controller;

import com.tecocinamos.dto.AlergenoResponseDTO;
import com.tecocinamos.service.AlergenoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alergenos")
public class AlergenoController {

    @Autowired
    private AlergenoServiceI alergenoService;

    /**
     * POST /api/v1/alergenos?nombre=xxxx
     * Crear nuevo alérgeno (solo ADMIN).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AlergenoResponseDTO> crear(@RequestParam String nombre) {
        AlergenoResponseDTO creado = alergenoService.crear(nombre);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * GET /api/v1/alergenos
     * Listar todos los alérgenos (público).
     */
    @GetMapping
    public ResponseEntity<List<AlergenoResponseDTO>> listar() {
        List<AlergenoResponseDTO> lista = alergenoService.listar();
        return ResponseEntity.ok(lista);
    }

    /**
     * DELETE /api/v1/alergenos/{id}
     * Eliminar alérgeno (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        alergenoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
