package com.tecocinamos.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredienteDetalleDTO {
    private Integer ingredienteId;
    private String nombreIngrediente;
    private BigDecimal cantidadUsada;
    private String unidad;
}