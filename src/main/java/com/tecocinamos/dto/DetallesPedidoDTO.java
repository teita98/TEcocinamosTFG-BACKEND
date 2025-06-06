package com.tecocinamos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesPedidoDTO {
    @NotNull(message = "El platoId es obligatorio")
    private Integer platoId;

    @NotNull(message = "La cantidad de platos es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidadPlato;

    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    private BigDecimal descuento; // opcional
}