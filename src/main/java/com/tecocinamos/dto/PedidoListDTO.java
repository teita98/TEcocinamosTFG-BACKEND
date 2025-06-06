package com.tecocinamos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoListDTO {
    private Integer id;
    private String nombreCliente;
    private String estado;
    private LocalDate fechaEntrega;
    private Integer totalPlatos;
    private BigDecimal total;
}