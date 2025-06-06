package com.tecocinamos.controller;

import com.tecocinamos.dto.ProveedorRequestDTO;
import com.tecocinamos.dto.ProveedorResponseDTO;
import com.tecocinamos.service.ProveedorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorServiceI proveedorService;

    //POST /api/v1/proveedores → crear un nuevo porveedor
    @PostMapping
    public ResponseEntity<ProveedorResponseDTO> crear(@RequestBody ProveedorRequestDTO dto) {
        ProveedorResponseDTO creado = proveedorService.crearProveedor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    //GET /api/v1/proveedores → Obtener los proveedores
    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    // GET /api/v1/porveedores/{id} --> obtener un porveedor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    //PUT /api/v1/proveedores/{id} → actualizar proveedor
    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO> actualizar(@PathVariable Integer id,
                                                           @RequestBody ProveedorRequestDTO dto) {
        return ResponseEntity.ok(proveedorService.actualizarProveedor(id, dto));
    }

    //DELETE /api/v1/proveedores/{id} → eliminar un proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        proveedorService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
