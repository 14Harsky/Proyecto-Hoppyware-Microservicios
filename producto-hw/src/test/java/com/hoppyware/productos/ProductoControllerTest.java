package com.hoppyware.productos;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;

import com.hoppyware.productos.controller.ProductoController;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {

    @Autowired
    private ProductoController productoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(productoController).isNotNull();
    }
    
    @Test
    void devuelveListaProducto() throws Exception {
        this.mockMvc.perform(get("/hoppyware/v1/producto")).andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    void devuelveProducto() throws Exception {
        this.mockMvc.perform(get("/hoppyware/v1/producto/1")).andDo(print())
        .andExpect(status().isOk()).andExpect(content().json("""
                {
                    "id": 1,
                    "nombre": "Kit de fermentación",
                    "descripcion": "Incluye balde, airlock y termómetro",
                    "proveedor": "BrewChile",
                    "precio": 35000,
                    "stock": 50,
                    "origen": "Santiago, Chile"
                }
                """));
    }

    @Test
    void devuelveGuardarLista() throws Exception {
        this.mockMvc.perform(post("/hoppyware/v1/producto/guardar")
        .contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "nombre": "Cerveza Testera",
                    "descripcion": "Descripcion de Cerveza Testera",
                    "proveedor": "Proveedor Testera",
                    "precio": 8000,
                    "stock": 50,
                    "origen": "Origen Testera"
                }
                """).accept(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    void devuelveActualizarStock() throws Exception {
        this.mockMvc.perform(put("/hoppyware/v1/producto/{id}/stock", 4)
        .param("cantidadComprada", "10").contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "id": 4,
                    "nombre": "Cerveza Prueba",
                    "descripcion": "Cerveza de prueba",
                    "proveedor": "Proveedor probador",
                    "precio": 11900,
                    "stock": 60,
                    "origen": "Origen Test"
                }
                """))
        .andExpect(status().isOk());
    }

    @Test
    void devuelveEliminarProducto() throws Exception {
        this.mockMvc.perform(delete("/hoppyware/v1/producto/eliminar/{id}", 3))
            .andExpect(status().isNoContent());
    }

}