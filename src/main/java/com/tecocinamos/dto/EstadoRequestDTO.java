package com.tecocinamos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoRequestDTO {
    @NotBlank(message = "El nombre del estado es obligatorio")
    private String nombre;
}