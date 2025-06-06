package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.model.Rol;
import com.tecocinamos.model.Usuario;
import com.tecocinamos.repository.RolRepository;
import com.tecocinamos.repository.UsuarioRepository;
import com.tecocinamos.security.CustomUserDetailsService;
import com.tecocinamos.security.JwtUtils;
import com.tecocinamos.service.UsuarioServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioServiceI {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public UsuarioResponseDTO registrarUsuario(UsuarioRegisterDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setTelefono(dto.getTelefono());

        // Por defecto asignamos el rol CLIENTE
        Rol rol = rolRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        Usuario guardado = usuarioRepository.save(usuario);

        System.out.println("Usuario registrado: " + usuario.getEmail());

        return convertirAResponseDTO(guardado);
    }

    @Override
    public AuthResponseDTO login(UsuarioLoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getContrasena())
        );

        UserDetails user = userDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtUtils.generateToken(user);

        System.out.println("Login exitoso para: " + dto.getEmail());

        return new AuthResponseDTO(token);
    }


    @Override
    public UsuarioResponseDTO obtenerPerfilPropio() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();  // El email viene del token

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Perfil consultado para: " + email);

        return convertirAResponseDTO(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> buscarUsuariosPorNombre(String nombre) {
        List<Usuario> usuarios = usuarioRepository.findByNombreStartingWithIgnoreCaseAndEliminadoFalse(nombre);

        System.out.println("Buscando usuarios que empiezan por: " + nombre);

        return usuarios.stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }


    @Override
    public UsuarioPublicDTO verPerfilPublico(String nombre) {
        Usuario usuario = usuarioRepository.findByNombreIgnoreCaseAndEliminadoFalse(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioPublicDTO dto = new UsuarioPublicDTO();
        dto.setNombre(usuario.getNombre());
        dto.setTelefono(usuario.getTelefono());

        System.out.println("Buscando usuario: " + nombre);

        return dto;
    }


    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findByIdAndEliminadoFalse(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        System.out.println("Buscando usuario con id: " + id);

        return convertirAResponseDTO(usuario);
    }

    @Override
    public void eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findByIdAndEliminadoFalse(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEliminado(true);
        usuario.setFechaEliminado(java.time.LocalDate.now());

        usuarioRepository.save(usuario);

        System.out.println("🗑Usuario marcado como eliminado: " + usuario.getEmail());

    }

    @Override
    public void cambiarPassword(CambiarPasswordDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getActual(), usuario.getContrasena())) {
            throw new RuntimeException("La contraseña actual no es válida");
        }

        usuario.setContrasena(passwordEncoder.encode(dto.getNueva()));
        usuarioRepository.save(usuario);

        System.out.println("Contraseña actualizada para " + email);
    }

    @Override
    public UsuarioResponseDTO actualizarPerfil(ActualizarPerfilDTO dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setTelefono(dto.getTelefono());

        return convertirAResponseDTO(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioResponseDTO> listarTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> !usuario.getEliminado())
                .map(this::convertirAResponseDTO)
                .toList();
    }


    private UsuarioResponseDTO convertirAResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setRol(usuario.getRol().getNombreRol());
        return dto;
    }
}
