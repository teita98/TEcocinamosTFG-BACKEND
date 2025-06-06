package com.tecocinamos.controller;


import com.tecocinamos.dto.EstadoRequestDTO;
import com.tecocinamos.dto.EstadoResponseDTO;
import com.tecocinamos.service.EstadoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/estados")
public class EstadoController {

    @Autowired
    private EstadoServiceI estadoService;

    //POST /api/v1/estados → crear estado
    @PostMapping
    public ResponseEntity<EstadoResponseDTO> crearEstado(@RequestBody EstadoRequestDTO dto) {
        return ResponseEntity.ok(estadoService.crearEstado(dto));
    }

    //GET /api/v1/estados → listar todos
    @GetMapping
    public ResponseEntity<List<EstadoResponseDTO>> listarEstados() {
        return ResponseEntity.ok(estadoService.listarEstados());
    }

    //DELETE /api/v1/estados/{id} → eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstado(@PathVariable Integer id) {
        estadoService.eliminarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
