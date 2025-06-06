package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.Rol;
import com.tecocinamos.model.Usuario;
import com.tecocinamos.repository.RolRepository;
import com.tecocinamos.repository.UsuarioRepository;
import com.tecocinamos.security.CustomUserDetailsService;
import com.tecocinamos.security.JwtUtils;
import com.tecocinamos.service.UsuarioServiceI;
import com.tecocinamos.service.mail.MailServiceI;
import com.tecocinamos.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioServiceI {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private com.tecocinamos.security.CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailServiceI mailServiceI;

    @Override
    public UsuarioResponseDTO registrarUsuario(UsuarioRegisterDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("Ya existe un usuario con ese email");
        }
        Rol rol = rolRepository.findByNombreRolIgnoreCase("CLIENTE")
                .orElseThrow(() -> new NotFoundException("Rol CLIENTE no encontrado"));
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .contrasena(passwordEncoder.encode(dto.getContrasena()))
                .telefono(dto.getTelefono())
                .rol(rol)
                .eliminado(false)
                .build();
        Usuario guardado = usuarioRepository.save(usuario);

        // Enviar email de bienvenida (HTML o texto plano)
        String to = guardado.getEmail();
        String subject = "¡Bienvenido a Tecocinamos!";
        String htmlBody = """
                <html>
                  <body>
                    <h2>Hola, %s!</h2>
                    <p>Gracias por registrarte en <b>Tecocinamos</b>.</p>
                    <p>Ya puedes explorar nuestro menú, hacer pedidos y disfrutar de nuestros servicios de catering.</p>
                    <br>
                    <p>Un saludo,<br>El equipo de Tecocinamos</p>
                  </body>
                </html>
                """.formatted(guardado.getNombre());

        mailServiceI.sendHtmlEmail(to, subject, htmlBody);

        return UsuarioResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .email(guardado.getEmail())
                .telefono(guardado.getTelefono())
                .rol(guardado.getRol().getNombreRol())
                .build();
    }

    @Override
    public AuthResponseDTO login(UsuarioLoginDTO dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getContrasena())
            );
        } catch (BadCredentialsException ex) {
            throw new BadRequestException("Credenciales inválidas");
        }
        UserDetails user = userDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtUtils.generateToken(user);
        return new AuthResponseDTO(token);
    }

    @Override
    public UsuarioResponseDTO obtenerPerfilPropio() {
        String email = SecurityUtil.getAuthenticatedEmail();
        Usuario usuario = usuarioRepository.findByEmailAndEliminadoFalse(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .rol(usuario.getRol().getNombreRol())
                .build();
    }

    @Override
    public List<UsuarioResponseDTO> buscarUsuariosPorNombre(String nombre) {
        // Solo ADMIN puede invocar este método (en controlador se protegerá)
        List<Usuario> usuarios = usuarioRepository.findByNombreStartingWithIgnoreCaseAndEliminadoFalse(nombre);
        return usuarios.stream()
                .map(u -> UsuarioResponseDTO.builder()
                        .id(u.getId())
                        .nombre(u.getNombre())
                        .email(u.getEmail())
                        .telefono(u.getTelefono())
                        .rol(u.getRol().getNombreRol())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioPublicDTO verPerfilPublico(String nombre) {
        Usuario usuario = usuarioRepository.findByNombreIgnoreCaseAndEliminadoFalse(nombre)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return new UsuarioPublicDTO(usuario.getNombre(), usuario.getTelefono());
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findByIdAndEliminadoFalse(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID " + id));
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .rol(usuario.getRol().getNombreRol())
                .build();
    }

    @Override
    public void eliminarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findByIdAndEliminadoFalse(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID " + id));
        usuario.setEliminado(true);
        usuario.setFechaEliminado(java.time.LocalDate.now());

        // Anonimizar campos únicos:
        // Usamos el propio ID para formar una clave irrepetible: "anonimo<ID>"
        String anonimoName = "anonimo" + usuario.getId();
        usuario.setNombre(anonimoName);

        // Para el email, construimos algo que no se repita y no colisione con ningún email real:
        String anonimoEmail = anonimoName + usuario.getId() + "@deleted.tecocinamos";
        usuario.setEmail(anonimoEmail);

        // Para el contraseña, construimos algo que no se repita y no colisione con ningún email real:
        String anonimoContrasena = anonimoName + usuario.getId();
        usuario.setContrasena(anonimoContrasena);

        // Vaciamos el teléfono (o ponemos un valor fijo, p.ej. cadena vacía)
        usuario.setTelefono("");

        usuarioRepository.save(usuario);
    }

    @Override
    public void cambiarPassword(CambiarPasswordDTO dto) {
        String email = SecurityUtil.getAuthenticatedEmail();
        Usuario usuario = usuarioRepository.findByEmailAndEliminadoFalse(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getActual(), usuario.getContrasena())) {
            throw new BadRequestException("La contraseña actual no es válida");
        }
        usuario.setContrasena(passwordEncoder.encode(dto.getNueva()));
        usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioResponseDTO actualizarPerfil(ActualizarPerfilDTO dto) {
        String email = SecurityUtil.getAuthenticatedEmail();
        Usuario usuario = usuarioRepository.findByEmailAndEliminadoFalse(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setTelefono(dto.getTelefono());
        Usuario actualizado = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .email(actualizado.getEmail())
                .telefono(actualizado.getTelefono())
                .rol(actualizado.getRol().getNombreRol())
                .build();
    }

    @Override
    public List<UsuarioResponseDTO> listarTodosLosUsuarios() {
        return usuarioRepository.findAll().stream()
                .filter(u -> !u.getEliminado())
                .map(u -> UsuarioResponseDTO.builder()
                        .id(u.getId())
                        .nombre(u.getNombre())
                        .email(u.getEmail())
                        .telefono(u.getTelefono())
                        .rol(u.getRol().getNombreRol())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO asignarRol(Integer usuarioId, Integer rolId) {
        Usuario usuario = usuarioRepository.findByIdAndEliminadoFalse(usuarioId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID " + usuarioId));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado con ID " + rolId));
        usuario.setRol(rol);
        Usuario actualizado = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .email(actualizado.getEmail())
                .telefono(actualizado.getTelefono())
                .rol(actualizado.getRol().getNombreRol())
                .build();
    }
}
