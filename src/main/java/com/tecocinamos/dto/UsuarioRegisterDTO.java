package com.tecocinamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegisterDTO {
    private String nombre;
    private String email;
    private String contrasena;
    private String telefono;
}
