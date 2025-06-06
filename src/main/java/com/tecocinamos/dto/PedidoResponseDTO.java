package com.tecocinamos.dto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponseDTO {
    private Integer id;
    private String estado;
    private String nombreCliente;
    private String emailCliente;
    private LocalDate fechaCreado;
    private LocalDate fechaEntrega;
    private String direccionEntrega;
    private BigDecimal total;
    private LocalDateTime fechaActualizacion;
    private List<DetallesPedidoDTO> detalles;
}