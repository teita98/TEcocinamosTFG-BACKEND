package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.PlatoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platos")
public class PlatoController {

    @Autowired
    private PlatoServiceI platoService;

    // POST /api/v1/platos → crear nuevo plato (admin)
    @PostMapping
    public ResponseEntity<PlatoResponseDTO> crearPlato(@RequestBody PlatoRequestDTO dto) {
        PlatoResponseDTO creado = platoService.crearPlato(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // GET /api/v1/platos → obtener todos los platos
    @GetMapping
    public ResponseEntity<List<PlatoListDTO>> listarPlatos() {
        return ResponseEntity.ok(platoService.listarPlatos());
    }

    // GET /api/v1/platos/{id} → obtener plato por ID
    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(platoService.obtenerPlatoPorId(id));
    }


    // GET /api/v1/platos/categoria/{id} → obtener platos por categoría


    // GET /api/v1/platos/categoria?nombre=
    @GetMapping("/categoria")
    public ResponseEntity<List<PlatoListDTO>> buscarPorCategoria(@RequestParam("nombre") String categoria) {
        return ResponseEntity.ok(platoService.buscarPorCategoria(categoria));
    }

    // GET /api/v1/platos/{id}/alergenos → obtener alérgenos del plato
    @GetMapping("/{id}/alergenos")
    public ResponseEntity<List<AlergenoResponseDTO>> obtenerAlergenos(@PathVariable Integer id) {
        return ResponseEntity.ok(platoService.obtenerAlergenosPorPlato(id));
    }



    // GET /api/v1/platos/{id}/ingredientes → obtener ingredientes del plato
    @GetMapping("/{id}/ingredientes")
    public ResponseEntity<List<IngredienteUsadoDTO>> obtenerIngredientes(@PathVariable Integer id) {
        return ResponseEntity.ok(platoService.obtenerIngredientesPorPlato(id));
    }


    // GET /platos/buscar?nombre= -> Buscar platos por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<PlatoListDTO>> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok(platoService.buscarPorNombre(nombre));
    }

    // PUT /api/v1/platos/{id} → actualizar plato (admin)
    @PutMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> actualizarPlato(@PathVariable Integer id,
                                                            @RequestBody PlatoRequestDTO dto) {
        PlatoResponseDTO actualizado = platoService.actualizarPlato(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE /api/v1/platos/{id} → eliminar o desactivar plato
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Integer id) {
        platoService.eliminarPlato(id);
        return ResponseEntity.noContent().build();
    }
}
