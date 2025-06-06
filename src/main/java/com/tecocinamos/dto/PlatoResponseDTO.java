package com.tecocinamos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoResponseDTO {
    private Integer id;
    private String nombrePlato;
    private String cantidad;
    private BigDecimal precio;
    private Integer stock;
    private String preparacionCasa;
    private String recomendaciones;
    private String categoriaNombre;
    private List<IngredienteDetalleDTO> ingredientes;
    private List<AlergenoResponseDTO> alergenos; // lista deduplicada de alérgenos
}