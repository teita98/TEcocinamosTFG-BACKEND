package com.tecocinamos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoRequestDTO {
    private String nombrePlato;
    private String cantidad; // por ración (ej: "250g")
    private BigDecimal precio;
    private Integer stock;
    private String preparacionCasa;
    private String recomendaciones;
    private Integer categoriaId;
    private List<IngredienteUsadoDTO> ingredientesUsados;
    private List<Integer> alergenosIds;

}


