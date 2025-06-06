package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.PlatoServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/platos")
public class PlatoController {

    @Autowired
    private PlatoServiceI platoService;


    /**
     * POST /api/v1/platos
     * Crear nuevo plato (solo ADMIN).
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlatoResponseDTO> crearPlato(@Valid @RequestBody PlatoRequestDTO dto) {
        PlatoResponseDTO creado = platoService.crearPlato(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * GET /api/v1/platos?page=&size=
     * Lista paginada de todos los platos (público).
     */
    @GetMapping
    public ResponseEntity<Page<PlatoListDTO>> listarPlatos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombrePlato"));
        Page<PlatoListDTO> dtoPage = (Page<PlatoListDTO>) platoService.listarPlatos(pageable);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * GET /api/v1/platos/{id}
     * Obtener plato por ID (público).
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        PlatoResponseDTO plato = platoService.obtenerPlatoPorId(id);
        return ResponseEntity.ok(plato);
    }


    /**
     * GET /api/v1/platos/categoria?nombre=&page=&size=
     * Buscar platos por categoría (público).
     */
    @GetMapping("/categoria")
    public ResponseEntity<Page<PlatoListDTO>> buscarPorCategoria(
            @RequestParam("nombre") String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombrePlato"));
        Page<PlatoListDTO> dtoPage = platoService.buscarPorCategoria(categoria, pageable);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * GET /api/v1/platos/buscar?nombre=&page=&size=
     * Buscar platos por nombre (público).
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<PlatoListDTO>> buscarPorNombre(
            @RequestParam("nombre") String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombrePlato"));
        Page<PlatoListDTO> dtoPage = platoService.buscarPorNombre(nombre, pageable);
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * GET /api/v1/platos/{id}/ingredientes
     * Obtener lista de ingredientes (detalles) de un plato (público).
     */
    @GetMapping("/{id}/ingredientes")
    public ResponseEntity<List<IngredienteDetalleDTO>> obtenerIngredientes(@PathVariable Integer id) {
        List<IngredienteDetalleDTO> lista = platoService.obtenerIngredientesPorPlato(id);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/v1/platos/{id}/alergenos
     * Obtener lista deduplicada de alérgenos de un plato (público).
     */
    @GetMapping("/{id}/alergenos")
    public ResponseEntity<List<AlergenoResponseDTO>> obtenerAlergenos(@PathVariable Integer id) {
        List<AlergenoResponseDTO> lista = platoService.obtenerAlergenosPorPlato(id);
        return ResponseEntity.ok(lista);
    }

    /**
     * PUT /api/v1/platos/{id}
     * Actualizar plato (solo ADMIN).
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PlatoResponseDTO> actualizarPlato(@PathVariable Integer id,
                                                            @Valid @RequestBody PlatoRequestDTO dto) {
        PlatoResponseDTO actualizado = platoService.actualizarPlato(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * DELETE /api/v1/platos/{id}
     * Eliminar plato (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Integer id) {
        platoService.eliminarPlato(id);
        return ResponseEntity.noContent().build();
    }

}
