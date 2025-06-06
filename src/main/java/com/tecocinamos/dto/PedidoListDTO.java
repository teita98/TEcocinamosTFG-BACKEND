package com.tecocinamos.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PedidoListDTO {
    private Integer id;
    private String nombreCliente;
    private String estado;
    private LocalDate fechaEntrega;
    private Integer totalPlatos;
}
