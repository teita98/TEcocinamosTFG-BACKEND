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
public class IngredienteUsadoDTO {
    private String nombre;
    private Integer ingredienteId;
    private BigDecimal cantidadUsada;
    private String unidad;
}
