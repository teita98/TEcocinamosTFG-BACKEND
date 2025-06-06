package com.tecocinamos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "plato")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plato_id")
    private Integer id;

    @Column(name = "nombre_plato", nullable = false, length = 50)
    private String nombrePlato;

    @Column(name = "cantidad", length = 50)
    private String cantidad;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    // Añadimos @Builder.Default para que stock=0 sea usado por el builder
    @Builder.Default
    @Column(name = "stock")
    private Integer stock = 0;

    @Column(name = "preparacion_casa", length = 200)
    private String preparacionCasa;

    @Column(name = "recomendaciones", length = 200)
    private String recomendaciones;

    @Column(name = "image_base_name", length = 100)
    private String imageBaseName;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallesPedido> detallesPedidos;

    // Añadimos @Builder.Default para que sea usado por el builder
    @Builder.Default
    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlatoIngrediente> ingredientes = new ArrayList<>();
}

