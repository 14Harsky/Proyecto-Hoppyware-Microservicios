package com.pedidos.hw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Clase que consume datos desde el endpoint de usuarios. Los cuales son
// mostrados como datos de contacto para el servicio de pedidos
public class UsuarioDto {

    @Schema(description = "Nombre de pila del usuario", example = "Carlos")
    private String pnombre;

    @Schema(description = "Apellido paterno del usuario", example = "González")
    private String appaterno;

    @Schema(description = "Correo electrónico de contacto", example = "carlos@example.com")
    private String correo;

    @Schema(description = "Número de teléfono", example = "+56912345678")
    private String num_telefono;
}
