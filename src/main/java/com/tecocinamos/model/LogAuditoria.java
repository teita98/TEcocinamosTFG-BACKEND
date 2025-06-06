package com.tecocinamos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "log_auditoria")
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer id;

    @Column(name = "entidad", length = 50)
    private String entidad;

    @Column(name = "campo_modificado", length = 50)
    private String campoModificado;

    @Column(name = "valor_anterior", length = 50)
    private String valorAnterior;

    @Column(name = "valor_nuevo", length = 50)
    private String valorNuevo;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "accion", length = 100)
    private String accion;

    @ManyToOne
    @JoinColumn(name = "usuario_admin_id")
    private Usuario usuarioAdmin;

    // Getters y setters
}

