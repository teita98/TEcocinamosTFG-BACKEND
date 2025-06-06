package com.tecocinamos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorRequestDTO {
    @NotBlank(message = "El nombre del proveedor es obligatorio")
    private String nombre;

    @NotBlank(message = "El contacto es obligatorio")
    private String contacto;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9()+\\- ]{7,20}$", message = "Formato de teléfono inválido")
    private String telefono;

    @Email(message = "Formato de email inválido")
    private String email;
}