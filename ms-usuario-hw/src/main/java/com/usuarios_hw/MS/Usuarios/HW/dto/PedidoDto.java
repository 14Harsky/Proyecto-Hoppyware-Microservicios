package com.usuarios_hw.MS.Usuarios.HW.dto;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {

        private Long id;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private Date fecha_pedido;
        private Integer estado;
        private Integer costoTotal;
        // Calculo del costo total de un pedido obteniendo los detalles asociados a un pedido especifico
        public Integer getcostoTotal(){
                int total = 0;
                for (DetallePedidoDTO pedido : detalles) {
                        total += pedido.getProducto().getPrecio() * pedido.getCantidad();
                }
                this.costoTotal = total;
                return costoTotal;
        }
        // Los datos de estado (integer) se pasan a un String con informacion del pedido
        @JsonIgnore
        public String getEstado(){
                if (estado == null) return "Desconocido";
                return switch (estado) {
                        case 0 -> "Recibido";
                        case 1 ->"En proceso";
                        case 2 ->"Despachado";
                        default -> "Otro";              
                };
        }
        private List<DetallePedidoDTO> detalles;
        
        

        
        
}
