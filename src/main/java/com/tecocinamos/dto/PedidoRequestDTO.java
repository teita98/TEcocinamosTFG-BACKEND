package com.tecocinamos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {
    @NotNull(message = "La fecha de entrega es obligatoria")
    @FutureOrPresent(message = "La fecha de entrega no puede ser pasada")
    private LocalDate fechaEntrega;

    @NotBlank(message = "La dirección de entrega es obligatoria")
    private String direccionEntrega;

    @NotNull(message = "Debe incluir detalles del pedido")
    @Size(min = 1, message = "Debe haber al menos un detalle de pedido")
    private List<DetallesPedidoDTO> detalles;
}