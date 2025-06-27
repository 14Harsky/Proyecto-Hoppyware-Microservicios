package com.pedidos.hw.dto;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDTO extends RepresentationModel<DetallePedidoDTO>{
    
    @Schema(description = "Cantidad de un producto", example = "2")
    private Integer cantidad;
    @JsonIgnore
    private Long id_producto;
    private ProductoDTO producto;
    


}
