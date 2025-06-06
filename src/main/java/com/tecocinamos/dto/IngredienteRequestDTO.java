package com.tecocinamos.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredienteRequestDTO {
    @NotBlank(message = "El nombre del ingrediente es obligatorio")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "El proveedorId es obligatorio")
    private Integer proveedorId;

    @NotNull(message = "La cantidad del envase es obligatoria")
    @DecimalMin(value = "0.1", message = "La cantidad del envase debe ser mayor que cero")
    private BigDecimal cantidadEnvase;

    @NotBlank(message = "La unidad del envase es obligatoria")
    private String unidadEnvase;

    @NotNull(message = "El precio del envase es obligatorio")
    @DecimalMin(value = "0.1", message = "El precio del envase debe ser mayor que cero")
    private BigDecimal precioEnvase;

    @NotBlank(message = "La unidad de uso es obligatoria")
    private String unidad;

    /**
     * Lista de IDs de alérgenos que contiene este ingrediente.
     * Si está vacío o nulo, no se asocian alérgenos.
     */
    private List<Integer> alergenosIds;
}