package com.tecocinamos.util;

import com.tecocinamos.model.LogAuditoria;
import com.tecocinamos.model.Usuario;
import com.tecocinamos.repository.LogAuditoriaRepository;
import com.tecocinamos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Utilidad para registrar entradas en la tabla de auditoría.
 */
@Component
public class LogAuditoriaUtil {

    @Autowired
    private LogAuditoriaRepository logAuditoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Registra una entrada en la tabla de auditoría.
     * @param entidad Nombre de la entidad (ej. "Pedido", "Plato")
     * @param campo   Campo modificado
     * @param anterior Valor anterior
     * @param nuevo   Valor nuevo
     * @param accion  Descripción de la acción (ej. "Cambio de estado")
     * @param emailAdmin  Email del usuario admin que realiza la acción
     */
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
