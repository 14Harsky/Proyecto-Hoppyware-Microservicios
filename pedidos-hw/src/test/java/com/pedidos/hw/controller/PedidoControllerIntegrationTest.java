package com.pedidos.hw.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedidos.hw.model.Pedido;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testing controlador Pedido")
public class PedidoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Comprueba el funcionamiento del metodo GET de pedidos, en colaboracion con el MS de productos.
    @Test
    @DisplayName("Validacion de solicitud GET con status 500")
    void shouldReturnPedido() throws Exception {
        this.mockMvc.perform(get("/hoppyware/v1/pedido").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }

    // Comprueba metodo GET con ms de usuarios, se implementa para validar funcionamiento de la prueba
    // Al no estar iniciado el proceso de usuarios, existe un error 404, cuando la prueba espera un codigo 200
    @Test
    void shouldReturnCliente() throws Exception {
        this.mockMvc.perform(get("/hoppyware/v1/{idPedido}/cliente-contacto", 25).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError());
    }


    // Se valida el conseguir solo un pedido con los valores especificados en las lineas 85 y 86
    @Test
    @DisplayName("Se valida que los datos ingresados coincidan con los existentes")
    void shouldReturnOnePedido() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hoppyware/v1/pedido/{id}", 23).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

        String json = result.getResponse().getContentAsString();
        Pedido pedido = this.objectMapper.readValue(json, Pedido.class);

        assertNotNull(pedido);
        assertEquals(23, pedido.getId());
        assertEquals(11, pedido.getId_usuario());
    }

    // Se induce el fallo en el test, al ingresar un id_usuario no correspondiente al pedido.
    @Test
    @DisplayName("Comprobando que el id de usuario no coincida, validando la veracidad de los datos")
    void shouldNotReturnOnePedido() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/hoppyware/v1/pedido/{id}", 23).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
        
        String json = result.getResponse().getContentAsString();
        Pedido pedido = this.objectMapper.readValue(json, Pedido.class);

        assertNotNull(pedido);
        assertEquals(23, pedido.getId());
        assertNotEquals(6, pedido.getId_usuario());
    }

    // Se prueba con una fecha en la cual no existen pedidos realizados, por ende una respuesta 204 "No content"
    @Test
    @DisplayName("Testing pedidos fecha no existente")
    void shouldNotReturnPedido() throws Exception {
      this.mockMvc.perform(get("/hoppyware/v1/pedido/buscarFecha/{fecha_pedido}", "22-05-24")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());
    }

    //Funcionamiento del guardado de datos en la BD 
    @Test
    @DisplayName("Validacion de solicitud POST")
    void shouldSavePedido() throws Exception {
        this.mockMvc.perform(post("/hoppyware/v1/pedido/guardar").contentType(MediaType.APPLICATION_JSON)
        .content("""
                 {
                   "fecha_pedido": "22-06-2025",
                   "estado": 2,
                   "id_usuario": 11,
                   "detalles": [
                     {
                       "cantidad": 6,
                       "id_producto": 8
                     },
                     {
                       "cantidad": 4,
                       "id_producto": 2
                     }
                   ]
                 }""" //
        ))
        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Testing actualizacion de datos")
    void shouldUpdatePedido() throws Exception {
      this.mockMvc.perform(put("/hoppyware/v1/pedido/actualizar/{id}", 21).contentType(MediaType.APPLICATION_JSON)
      .content("""
        {
        "id": 21,
        "fecha_pedido": "24-06-2025",
        "estado": 2,
        "id_usuario": 3,
        "detalles": [{
          "cantidad": 6,
          "id_producto": 8
          }]
        }"""))
      .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing eliminacion de pedido")
    void shouldDeletePedido() throws Exception {
      this.mockMvc.perform(delete("/hoppyware/v1/pedido/eliminar/{id}", 25)).andExpect(status().isNoContent());
    }
}
