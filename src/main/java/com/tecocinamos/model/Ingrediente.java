package com.tecocinamos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingrediente_id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @Column(name = "cantidad_envase", nullable = false)
    private BigDecimal cantidadEnvase;

    @Column(name = "unidad_envase", nullable = false, length = 20)
    private String unidadEnvase;

    @Column(name = "precio_envase", nullable = false)
    private BigDecimal precioEnvase;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;

    @Column(name = "unidad", length = 20)
    private String unidad;

    @OneToMany(mappedBy = "ingrediente")
    private List<PlatoIngrediente> platos;

    // Getters y setters
}