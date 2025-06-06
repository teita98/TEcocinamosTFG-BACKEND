package com.tecocinamos.security;

import com.tecocinamos.model.Usuario;
import com.tecocinamos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByEmailAndEliminadoFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        String rol = user.getRol().getNombreRol(); // e.g. "ADMIN" o "CLIENTE"
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getContrasena(),
                authorities
        );
    }
}
