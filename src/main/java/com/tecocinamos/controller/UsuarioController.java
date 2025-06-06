package com.tecocinamos.controller;

import com.tecocinamos.dto.*;
import com.tecocinamos.service.UsuarioServiceI;
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

    // POST /api/v1/users/register
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRegisterDTO dto) {
        UsuarioResponseDTO response = usuarioService.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);    }

    //POST /api/v1/users/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UsuarioLoginDTO dto) {
        AuthResponseDTO token = usuarioService.login(dto);
        return ResponseEntity.ok(token);
    }


    // GET /api/v1/users/me -> Ver el perfil del usuario autenticado. METER TOKEN QUE DA POSTMAN AL HACER LOGIN
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getPerfil() {
        return ResponseEntity.ok(usuarioService.obtenerPerfilPropio());
    }


    // GET /api/v1/users/search?name=
    @GetMapping("/search")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarUsuarios(@RequestParam("name") String nombre) {
        List<UsuarioResponseDTO> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }


    // GET /api/v1/users/profile/{nombre}
    @GetMapping("/profile/{nombre}")
    public ResponseEntity<UsuarioPublicDTO> verPerfilPublico(@PathVariable String nombre) {
        UsuarioPublicDTO perfil = usuarioService.verPerfilPublico(nombre);
        return ResponseEntity.ok(perfil);
    }


    // GET /api/v1/users/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    // PUT /api/v1/users/password
    @PutMapping("/password")
    public ResponseEntity<Void> cambiarPassword(@RequestBody CambiarPasswordDTO dto) {
        usuarioService.cambiarPassword(dto);
        return ResponseEntity.noContent().build();
    }
    // PUT /api/v1/users/perfil
    @PutMapping("/perfil")
    public ResponseEntity<UsuarioResponseDTO> actualizarPerfil(@RequestBody ActualizarPerfilDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarPerfil(dto));
    }

    // GET /api/v1/users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosLosUsuarios());
    }

    // DELETE /api/v1/users/{id} -> Borrado lógico (no borra los datos de la bbdd)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
