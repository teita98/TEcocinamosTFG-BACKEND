// Paso 1: Añadir la utilidad de registro a una clase común
package com.tecocinamos.util;

import com.tecocinamos.model.LogAuditoria;
import com.tecocinamos.model.Usuario;
import com.tecocinamos.repository.LogAuditoriaRepository;
import com.tecocinamos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogAuditoriaUtil {

    @Autowired
    private LogAuditoriaRepository logAuditoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void registrar(String entidad, String campo, String anterior, String nuevo, String accion, String emailAdmin) {
        Usuario admin = usuarioRepository.findByEmail(emailAdmin)
                .orElseThrow(() -> new RuntimeException("Admin no encontrado para el log de auditoría"));

        LogAuditoria log = LogAuditoria.builder()
                .entidad(entidad)
                .campoModificado(campo)
                .valorAnterior(anterior)
                .valorNuevo(nuevo)
                .accion(accion)
                .fecha(LocalDateTime.now())
                .usuarioAdmin(admin)
                .build();

        logAuditoriaRepository.save(log);
    }
}
