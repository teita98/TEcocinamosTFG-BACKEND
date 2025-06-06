package com.tecocinamos.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class ContactFormDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
}
