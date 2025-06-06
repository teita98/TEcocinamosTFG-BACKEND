package com.tecocinamos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredienteResponseDTO {
    private Integer id;
    private String nombre;
    private String categoria;
    private String proveedor;       // nombre del proveedor
    private BigDecimal cantidadEnvase;
    private String unidadEnvase;
    private BigDecimal precioEnvase;
    private BigDecimal precioUnitario;
    private String unidad;
    private List<AlergenoResponseDTO> alergenos; // lista de alérgenos asociados
}