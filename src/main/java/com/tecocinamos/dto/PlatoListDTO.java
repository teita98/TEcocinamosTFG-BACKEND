package com.tecocinamos.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class PlatoListDTO {
    private Integer id;
    private String nombrePlato;
    private BigDecimal precio;
    private String categoria;
}
