package com.usuarios_hw.MS.Usuarios.HW.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {

    private Integer cantidad;
    @JsonIgnore    
    private Long id_producto;
    private ProductoDTO producto;
    

}
