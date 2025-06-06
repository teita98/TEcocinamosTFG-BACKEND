package com.tecocinamos.service;

import com.tecocinamos.dto.*;

import java.util.List;

public interface UsuarioServiceI {
    UsuarioResponseDTO registrarUsuario(UsuarioRegisterDTO dto);
    AuthResponseDTO login(UsuarioLoginDTO dto);
    UsuarioResponseDTO obtenerPerfilPropio();
    List<UsuarioResponseDTO> buscarUsuariosPorNombre(String nombre);
    UsuarioPublicDTO verPerfilPublico(String nombre);
    UsuarioResponseDTO obtenerUsuarioPorId(Integer id);
    void eliminarUsuario(Integer id);
    void cambiarPassword(CambiarPasswordDTO dto);
    UsuarioResponseDTO actualizarPerfil(ActualizarPerfilDTO dto);
    List<UsuarioResponseDTO> listarTodosLosUsuarios();
    UsuarioResponseDTO asignarRol(Integer usuarioId, Integer rolId);

}
