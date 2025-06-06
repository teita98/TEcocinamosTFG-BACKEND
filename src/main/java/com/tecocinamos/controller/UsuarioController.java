package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.UsuarioServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioController {

    @Autowired
    private UsuarioServiceI usuarioService;


    /**
     * GET /api/v1/users/me
     * Devuelve el perfil del usuario autenticado (ROLE_CLIENTE o ROLE_ADMIN).
     */
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> getPerfil() {
        UsuarioResponseDTO perfil = usuarioService.obtenerPerfilPropio();
        return ResponseEntity.ok(perfil);
    }

    /**
     * PUT /api/v1/users/password
     * Cambia la contraseña del usuario autenticado.
     */
    @PutMapping("/password")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<Void> cambiarPassword(@Valid @RequestBody CambiarPasswordDTO dto) {
        usuarioService.cambiarPassword(dto);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT /api/v1/users/perfil
     * Actualiza nombre y teléfono del usuario autenticado.
     */
    @PutMapping("/perfil")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> actualizarPerfil(@Valid @RequestBody ActualizarPerfilDTO dto) {
        UsuarioResponseDTO actualizado = usuarioService.actualizarPerfil(dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * GET /api/v1/users/profile/{nombre}
     * Devuelve perfil público (nombre, teléfono) de un usuario dado su nombre.
     * Ruta pública.
     */
    @GetMapping("/profile/{nombre}")
    public ResponseEntity<UsuarioPublicDTO> verPerfilPublico(@PathVariable String nombre) {
        UsuarioPublicDTO perfil = usuarioService.verPerfilPublico(nombre);
        return ResponseEntity.ok(perfil);
    }

    /**
     * GET /api/v1/users/search?name=
     * Busca usuarios por nombre (solo ADMIN).
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarios(@RequestParam("name") String nombre) {
        List<UsuarioResponseDTO> lista = usuarioService.buscarUsuariosPorNombre(nombre);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/v1/users
     * Lista todos los usuarios (solo ADMIN).
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<UsuarioResponseDTO> lista = usuarioService.listarTodosLosUsuarios();
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/v1/users/{id}
     * Obtiene usuario por ID (solo ADMIN).
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioPorId(@PathVariable Integer id) {
        UsuarioResponseDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * DELETE /api/v1/users/{id}
     * Elimina lógicamente usuario (solo ADMIN).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PUT /api/v1/users/{id}/rol/{rolId}
     * Asigna rol a usuario (solo ADMIN).
     */
    @PutMapping("/{id}/rol/{rolId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> asignarRol(
            @PathVariable Integer id,
            @PathVariable Integer rolId) {
        UsuarioResponseDTO actualizado = usuarioService.asignarRol(id, rolId);
        return ResponseEntity.ok(actualizado);
    }


}
