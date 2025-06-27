package com.pedidos.hw.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Collections;

import com.pedidos.hw.dto.*;
import com.pedidos.hw.service.PedidoService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(PedidoController.class)
public class PedidoControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PedidoService pedidoService;

    

    @Test
    void shouldReturnPedidos() throws Exception {
        // Detalle del producto
    ProductoDTO producto = new ProductoDTO();
    producto.setId(8L);
    producto.setNombre("Producto 8");
    producto.setPrecio(1000);

    DetallePedidoDTO detalle = new DetallePedidoDTO();
    detalle.setCantidad(6);
    detalle.setId_producto(8L);
    detalle.setProducto(producto);
    

    // Contacto del usuario
    UsuarioDto usuario = new UsuarioDto();
    usuario.setPnombre("Pedro");
    usuario.setAppaterno("Ruiz");
    usuario.setNum_telefono("940861596");
    usuario.setCorreo("hola@hola.cl");
    

    // Pedido DTO
    PedidoUsuarioDTO pedidoDTO = new PedidoUsuarioDTO();
    pedidoDTO.setId(1L);
    pedidoDTO.setEstado(2);
    pedidoDTO.setFecha_pedido(LocalDate.of(2025, 6, 22));
    pedidoDTO.setDetalles(Collections.singletonList(detalle));
    pedidoDTO.setContacto(usuario);
    

    when(pedidoService.listarPedidosDTO())
        .thenReturn(Collections.singletonList(pedidoDTO));

    mockMvc.perform(get("/hoppyware/v1/pedido"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id", is(1)))
           .andExpect(jsonPath("$[0].fecha_pedido", is("22-06-2025")))
           .andExpect(jsonPath("$[0].estado", is(2))); 
    }


}
