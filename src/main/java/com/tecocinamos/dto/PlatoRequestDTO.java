package com.tecocinamos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlatoRequestDTO {
    @NotBlank(message = "El nombre del plato es obligatorio")
    private String nombrePlato;

    @NotBlank(message = "La cantidad (por ración) es obligatoria")
    private String cantidad; // ej. "250g"

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.1", message = "El precio debe ser mayor que cero")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    private String preparacionCasa;   // opcional

    private String recomendaciones;   // opcional

    @NotNull(message = "La categoríaId es obligatoria")
    private Integer categoriaId;

    /**
     * Lista de ingredientes usados: cada uno especifica ingredienteId, cantidadUsada y unidad.
     * Puede ser nulo o vacío si el plato no tiene ingredientes detallados.
     */
    private List<IngredienteDetalleDTO> ingredientesUsados;

    /**
     * Lista de IDs de alérgenos. Aunque los alérgenos se heredan de los ingredientes,
     * este campo puede servir para añadir alérgenos extra (opcional).
     */
    private List<Integer> alergenosIds;
}
