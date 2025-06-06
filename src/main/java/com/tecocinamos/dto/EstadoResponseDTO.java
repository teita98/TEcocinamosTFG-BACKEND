package com.tecocinamos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoResponseDTO {
    private Integer id;
    private String nombre;
}
