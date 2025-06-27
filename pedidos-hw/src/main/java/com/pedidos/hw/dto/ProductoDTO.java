package com.pedidos.hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    @Schema(description = "ID del producto", example = "8", type = "integer", format = "int64")
    private Long id;
    @Schema(description = "Nombre del producto", example = "Malta Pilsner")
    private String nombre;
    @Schema(description = "Precio del producto en CLP", example = "12000", type = "integer", format = "int32")
    private Integer precio;
    @Schema(description = "Origen del producto", example = "Múnich, Alemania")
    private String origen;

}
