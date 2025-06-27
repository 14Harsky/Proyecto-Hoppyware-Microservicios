package com.usuarios_hw.MS.Usuarios.HW.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuarios_hw.MS.Usuarios.HW.dto.DetallePedidoDTO;
import com.usuarios_hw.MS.Usuarios.HW.dto.PedidoDto;
import com.usuarios_hw.MS.Usuarios.HW.dto.ProductoDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private UsuarioController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    void shouldReturnUserList() throws Exception {
        this.mockMvc.perform(get("/hoppyware/v1/usuario")).andDo(print()).andExpect(status().isOk());
    }

    // Modificar el JSON esperado provocará que las pruebas fallen, por ende se
    // comprueba el funcionamiento del test
    @Test
    void shouldReturnUser() throws Exception {
        this.mockMvc.perform(get("/hoppyware/v1/usuario/4")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\n" + //
                        "  \"id\": 4,\n" + //
                        "  \"run\": \"19543210\",\n" + //
                        "  \"dvrun\": \"K\",\n" + //
                        "  \"pnombre\": \"María\",\n" + //
                        "  \"snombre\": \"José\",\n" + //
                        "  \"appaterno\": \"Rojas\",\n" + //
                        "  \"apmaterno\": \"Díaz\",\n" + //
                        "  \"correo\": \"maria.rojas@example.com\",\n" + //
                        "  \"num_telefono\": \"+56998765432\",\n" + //
                        "  \"fecha_nacto\": \"21-07-1987\"\n" + //
                        "}"));
    }

    // Guardado de un recurso en la base de datos, si existen, arrojará error.
    @Test
    void shouldReturnPOST() throws Exception {
        this.mockMvc
                .perform(post("/hoppyware/v1/usuario/guardarLista").contentType(MediaType.APPLICATION_JSON)
                        .content("[\n" + //
                                "  {  \n" + //
                                "  \"run\": \"19533217\",\n" + //
                                "  \"dvrun\": \"K\",\n" + //
                                "  \"pnombre\": \"Lucia\",\n" + //
                                "  \"snombre\": \"Fernanda\",\n" + //
                                "  \"appaterno\": \"Rojas\",\n" + //
                                "  \"apmaterno\": \"Díaz\",\n" + //
                                "  \"correo\": \"luci_fer@example.com\",\n" + //
                                "  \"num_telefono\": \"+56988565632\",\n" + //
                                "  \"fecha_nacto\": \"2-04-2021\"\n" + //
                                "}\n" + //
                                "]")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .is4xxClientError());
    }

    @Test
    void shouldReturnUserRun() throws Exception {
        this.mockMvc
                .perform(get("/hoppyware/v1/usuario/{run}/usuario-run", "20432109").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUsuarioPedido() throws Exception {
        MvcResult resultado = this.mockMvc
                .perform(get("/hoppyware/v1/usuario/{id}/pedidos-cliente", 11).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String json = resultado.getResponse().getContentAsString();
        List<PedidoDto> pedidos = this.objectMapper.readValue(json, new TypeReference<List<PedidoDto>>(){});

        assertNotNull(pedidos);
        assertNotNull(pedidos.get(0).getDetalles());
        assertFalse(pedidos.isEmpty());

        List<DetallePedidoDTO> detalles = pedidos.get(0).getDetalles();
        assertNotNull(detalles);
        assertTrue(detalles.get(0).getCantidad() > 0);

        ProductoDTO producto = detalles.get(0).getProducto();
        assertNotNull(producto);
        assertNotNull(producto.getNombre());
    }

    @Test
    void shouldUpdateUsuario() throws Exception{
        this.mockMvc.perform(put("/hoppyware/v1/usuario/actualizar/{id}", 11).contentType(MediaType.APPLICATION_JSON)
        .content("""
                {
            "id": 11,
            "run": "21111222",
            "dvrun": "1",
            "pnombre": "Valentina",
            "snombre": "Beatriz",
            "appaterno": "Gonzalez",
            "apmaterno": "Vergara",
            "correo": "valentina.araya@example.com",
            "num_telefono": "+56911223344",
            "fecha_nacto": "24-04-1994"
            }
                """
            )).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing eliminacion de usuario")
    void shouldDeleteUsuario() throws Exception{
        this.mockMvc.perform(delete("/hoppyware/v1/usuario/eliminar/{id}", 5)).andExpect(status().isNoContent());
        
    }

}
