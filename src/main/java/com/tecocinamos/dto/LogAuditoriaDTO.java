package com.tecocinamos.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogAuditoriaDTO {
    private Integer id;
    private String entidad;
    private Integer entidadId;
    private String campoModificado;
    private String valorAnterior;
    private String valorNuevo;
    private String accion;
    private LocalDateTime fecha;
    private String emailAdmin;
}