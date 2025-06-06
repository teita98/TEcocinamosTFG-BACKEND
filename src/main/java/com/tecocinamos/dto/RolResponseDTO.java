package com.tecocinamos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolResponseDTO {
    private Integer id;
    private String nombreRol;
}