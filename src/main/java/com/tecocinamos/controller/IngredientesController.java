package com.tecocinamos.controller;

import com.tecocinamos.dto.IngredienteRequestDTO;
import com.tecocinamos.dto.IngredienteResponseDTO;
import com.tecocinamos.service.IngredienteServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredientes")
public class IngredientesController {

    @Autowired
    private IngredienteServiceI ingredienteService;

    //POST /api/v1/ingredientes → crear ingrediente (admin)
    @PostMapping
    public ResponseEntity<IngredienteResponseDTO> crearIngrediente(@RequestBody IngredienteRequestDTO dto) {
        IngredienteResponseDTO creado = ingredienteService.crearIngrediente(dto);
        return ResponseEntity.ok(creado);
    }

    //GET /api/v1/ingredientes → obtener todos los ingredientes
    @GetMapping
    public ResponseEntity<List<IngredienteResponseDTO>> listarIngredientes() {
        return ResponseEntity.ok(ingredienteService.listarIngredientes());
    }

    //GET /api/v1/ingredientes/{id} → obtener uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ingredienteService.obtenerIngredientePorId(id));
    }

    //PUT /api/v1/ingredientes/{id} → actualizar ingrediente
    @PutMapping("/{id}")
    public ResponseEntity<IngredienteResponseDTO> actualizar(@PathVariable Integer id, @RequestBody IngredienteRequestDTO dto) {
        IngredienteResponseDTO actualizado = ingredienteService.actualizarIngrediente(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    //DELETE /api/v1/ingredientes/{id} → eliminar ingrediente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ingredienteService.eliminarIngrediente(id);
        return ResponseEntity.noContent().build();
    }

    //GET /api/v1/ingredientes/proveedor/{id} → ingredientes por proveedor
}
