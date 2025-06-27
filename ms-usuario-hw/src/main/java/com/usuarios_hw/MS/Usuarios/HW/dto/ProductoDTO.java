package com.usuarios_hw.MS.Usuarios.HW.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private String nombre;
    private Integer precio;
    private String origen;

}
