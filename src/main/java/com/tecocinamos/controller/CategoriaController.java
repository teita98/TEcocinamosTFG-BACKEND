package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.CategoriaServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaServiceI categoriaService;

    /**
     * POST /api/v1/categorias
     * Crear categoría (solo ADMIN).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO creada = categoriaService.crearCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    /**
     * GET /api/v1/categorias
     * Listar todas las categorías (público).
     */
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {
        List<CategoriaResponseDTO> lista = categoriaService.listarCategorias();
        return ResponseEntity.ok(lista);
    }

//    //GET /api/v1/categorias/{id} -> Obtener categoria por id
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoriaResponseDTO> obtenerCategoria(@PathVariable Integer id) {
//        return ResponseEntity.ok(categoriaService.obtenerCategoriaPorId(id));
//    }

    /**
     * PUT /api/v1/categorias/{id}
     * Actualizar categoría (solo ADMIN).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO actualizado = categoriaService.actualizarCategoria(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * DELETE /api/v1/categorias/{id}
     * Eliminar categoría (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
