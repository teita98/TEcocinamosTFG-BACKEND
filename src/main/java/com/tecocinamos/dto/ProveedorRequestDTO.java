package com.tecocinamos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorRequestDTO {
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
}
