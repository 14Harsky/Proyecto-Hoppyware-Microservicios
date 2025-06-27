package com.pedidos.hw.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
//@Table(name = "detalle_pedido")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id_detalle;

    @Column(nullable = false)
    @Schema(description = "Cantidad de un producto", example = "2")
    private Integer cantidad;

    @Column(name = "id_producto")
    @Schema(description = "ID del producto asociado", example = "10")
    private Long id_producto;

    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    @JsonBackReference
    private Pedido pedido;

    

    

}
