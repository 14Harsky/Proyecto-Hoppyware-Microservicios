package com.pedidos.hw.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un pedido con información del usuario y detalles del pedido")
public class PedidoUsuarioDTO {

    @Schema(description = "ID del pedido", example = "4")
    private Long id;

    @Schema(description = "Estado del pedido (1 = pendiente, 2 = confirmado, etc.)", example = "2")
    private Integer estado;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Schema(description = "Fecha del pedido en formato dd-MM-yy", example = "22-06-25")
    private LocalDate fecha_pedido;

    @Schema(description = "Costo total del pedido calculado a partir de los productos", example = "48000")
    private Integer costo_total;

    // Calculo del costo total de un pedido, iterando sobre cada pedido presente en
    // un arreglo de detalles
    public Integer getcosto_total() {
        int total = 0;
        for (DetallePedidoDTO pedido : detalles) {
            total += pedido.getProducto().getPrecio() * pedido.getCantidad();
        }
        this.costo_total = total;
        return costo_total;
    }

    @Schema(description = "Datos de contacto del usuario que hizo el pedido")
    private UsuarioDto contacto;

    @Schema(description = "Lista de detalles que componen el pedido")
    private List<DetallePedidoDTO> detalles;
}
