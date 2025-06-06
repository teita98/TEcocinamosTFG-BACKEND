package com.tecocinamos.controller;


import com.tecocinamos.dto.AlergenoResponseDTO;
import com.tecocinamos.service.AlergenoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alergenos")
public class AlergenoController {

    @Autowired
    private AlergenoServiceI alergenoService;

    //POST /api/v1/alergenos?nombre=xxxx → crear nuevo
    @PostMapping
    public ResponseEntity<AlergenoResponseDTO> crear(@RequestParam String nombre) {
        return ResponseEntity.ok(alergenoService.crear(nombre));
    }

    //GET /api/v1/alergenos → listar todos
    @GetMapping
    public ResponseEntity<List<AlergenoResponseDTO>> listar() {
        return ResponseEntity.ok(alergenoService.listar());
    }

    //DELETE /api/v1/alergenos/{id} → eliminar alérgeno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        alergenoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
