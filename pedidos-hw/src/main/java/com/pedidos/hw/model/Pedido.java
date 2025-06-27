package com.pedidos.hw.model;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "pedido")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Modelo para representar un pedido")
// Clase base de Pedidos la cual funciona como tabla en Oracle SQL para el
// poblado de datos.
public class Pedido {

    // ID principal de pedidos, funciona como autogenerativo utilizando una
    // secuencia
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Formateo de los datos para recibir una fecha
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Fecha del pedido", example = "22-06-2025")
    private LocalDate fecha_pedido;

    @Column(nullable = false)
    @Schema(description = "Estado del pedido", example = "1")
    private Integer estado;

    // Esta columna genera la relacion con la tabla de usuarios
    @Column(name = "id_usuario", nullable = false)
    @Schema(description = "ID del usuario que realiza el pedido", example = "11")
    private Long id_usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Schema(description = "Lista de detalles asociados al pedido")
    private List<DetallePedido> detalles = new ArrayList<>();

}
