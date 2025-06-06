package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.UsuarioServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UsuarioServiceI usuarioService;

    /**
     * POST /api/v1/auth/register
     * Registra nuevo usuario (rol CLIENTE por defecto).
     */
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody UsuarioRegisterDTO dto) {
        UsuarioResponseDTO creado = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * POST /api/v1/auth/login
     * Autentica usuario y devuelve JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UsuarioLoginDTO dto) {
        AuthResponseDTO token = usuarioService.login(dto);
        return ResponseEntity.ok(token);
    }
}
