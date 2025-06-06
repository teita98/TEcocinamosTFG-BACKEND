package com.tecocinamos.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class IngredienteRequestDTO {
    private String nombre;
    private String categoria;
    private Integer proveedorId;
    private BigDecimal cantidadEnvase;
    private String unidadEnvase;
    private BigDecimal precioEnvase;
    private BigDecimal precioUnitario;
    private String unidad;
}
