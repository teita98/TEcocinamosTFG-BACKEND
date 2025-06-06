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
public class IngredienteResponseDTO {
    private Integer id;
    private String nombre;
    private String categoria;
    private String proveedor; // Nombre del proveedor
    private BigDecimal cantidadEnvase;
    private String unidadEnvase;
    private BigDecimal precioEnvase;
    private BigDecimal precioUnitario;
    private String unidad;
}
