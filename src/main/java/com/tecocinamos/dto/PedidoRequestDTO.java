package com.tecocinamos.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private LocalDate fechaEntrega;
    private String direccionEntrega;
    private List<DetallesPedidoDTO> detalles;
}
