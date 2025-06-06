package com.tecocinamos.controller;


import com.tecocinamos.dto.IngredienteRequestDTO;
import com.tecocinamos.dto.IngredienteResponseDTO;
import com.tecocinamos.dto.AlergenoResponseDTO;
import com.tecocinamos.service.IngredienteServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/ingredientes")
public class IngredientesController {

    @Autowired
    private IngredienteServiceI ingredienteService;

    /**
     * POST /api/v1/ingredientes
     * Crear ingrediente (solo ADMIN).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngredienteResponseDTO> crearIngrediente(@Valid @RequestBody IngredienteRequestDTO dto) {
        IngredienteResponseDTO creado = ingredienteService.crearIngrediente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * GET /api/v1/ingredientes?page=&size=
     * Lista paginada de ingredientes (público).
     */
    @GetMapping
    public ResponseEntity<Page<IngredienteResponseDTO>> listarIngredientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre"));
        Page<IngredienteResponseDTO> dtoPage = ingredienteService.listarIngredientes(pageable);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * GET /api/v1/ingredientes/{id}
     * Obtener ingrediente por ID (público).
     */
    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtenerPorId(@PathVariable Integer id) {
        IngredienteResponseDTO dto = ingredienteService.obtenerIngredientePorId(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * GET /api/v1/ingredientes/por-proveedor/{id}?page=&size=
     * Listar ingredientes de un proveedor (público).
     */
    @GetMapping("/por-proveedor/{id}")
    public ResponseEntity<Page<IngredienteResponseDTO>> listarPorProveedor(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre"));
        Page<IngredienteResponseDTO> dtoPage = ingredienteService.listarPorProveedor(id, pageable);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * GET /api/v1/ingredientes/{id}/alergenos
     * Obtener alérgenos de un ingrediente (público).
     */
    @GetMapping("/{id}/alergenos")
    public ResponseEntity<List<AlergenoResponseDTO>> getAlergenosPorIngrediente(@PathVariable Integer id) {
        IngredienteResponseDTO dto = ingredienteService.obtenerIngredientePorId(id);
        return ResponseEntity.ok(dto.getAlergenos());
    }

    /**
     * POST /api/v1/ingredientes/{ingredienteId}/alergenos/{alergenoId}
     * Añade el alérgeno (alergenoId) al ingrediente (ingredienteId).
     * Devuelve el IngredienteResponseDTO actualizado.
     */
    @PostMapping("/{ingredienteId}/alergenos/{alergenoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngredienteResponseDTO> agregarAlergeno(
            @PathVariable Integer ingredienteId,
            @PathVariable Integer alergenoId) {
        IngredienteResponseDTO actualizado = ingredienteService.agregarAlergenoIngrediente(ingredienteId, alergenoId);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * PUT /api/v1/ingredientes/{id}
     * Actualizar ingrediente (solo ADMIN).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<IngredienteResponseDTO> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody IngredienteRequestDTO dto) {
        IngredienteResponseDTO actualizado = ingredienteService.actualizarIngrediente(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * DELETE /api/v1/ingredientes/{id}
     * Eliminar ingrediente (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ingredienteService.eliminarIngrediente(id);
        return ResponseEntity.noContent().build();
    }
}
