package com.tecocinamos.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PlatoIngredienteDTO {
    private Integer ingredienteId;
    private String nombreIngrediente;
    private BigDecimal cantidadUsada;
    private String unidad;
}
