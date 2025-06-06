package com.tecocinamos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesPedidoDTO {
    private Integer platoId;
    private String nombrePlato;
    private Integer cantidadPlato;
    private BigDecimal descuento; // puede ser null o 0
}
