package com.pedidos.hw.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.pedidos.hw.model.DetallePedido;
import com.pedidos.hw.model.Pedido;

@DataJpaTest
@ActiveProfiles("test")
public class PedidoRepositoryUniTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    @DisplayName("Prueba de comunicación con el repositorio")
    void contextLoads() {
        assertThat(pedidoRepository).isNotNull();
    }

    @Test
    @DisplayName("Guardado de pedidos en el repositorio")
    void guardarPedido() {
        Pedido pedido = new Pedido();
        pedido.setFecha_pedido(LocalDate.of(2025, 6, 22));
        pedido.setEstado(2);
        pedido.setId_usuario(11L);

        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(6);
        detalle.setId_producto(9L);
        detalle.setPedido(pedido);

        pedido.getDetalles().add(detalle);

        Pedido pedguardado = pedidoRepository.save(pedido);

        assertNotNull(pedguardado.getId());
        assertEquals(1, pedguardado.getDetalles().size());
        assertEquals(9L, pedguardado.getDetalles().get(0).getId_producto());
    }

    // Listado de pedidos
    @Test
    @DisplayName("Listado de pedidos del repositorio")
    void listarPedidos() {
        // Guardado de primer pedido y sus detalles
        Pedido pedido1 = new Pedido();
        pedido1.setFecha_pedido(LocalDate.of(2025, 6, 22));
        pedido1.setEstado(2);
        pedido1.setId_usuario(11L);

        DetallePedido detalle1 = new DetallePedido();
        detalle1.setCantidad(6);
        detalle1.setId_producto(9L);
        detalle1.setPedido(pedido1);

        // Guardado de segundo pedido y sus detalles
        Pedido pedido2 = new Pedido();
        pedido2.setFecha_pedido(LocalDate.of(2025, 6, 21));
        pedido2.setEstado(1);
        pedido2.setId_usuario(10L);

        DetallePedido detalle2 = new DetallePedido();
        detalle2.setCantidad(3);
        detalle2.setId_producto(2L);
        detalle2.setPedido(pedido2);

        // Se agregan los detalles correspondientes a cada pedido
        pedido1.getDetalles().add(detalle1);
        pedido2.getDetalles().add(detalle2);

        // Guardado de pedidos en el repositorio
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);

        // Recuperación de todos los pedidos
        List<Pedido> listaPedidos = pedidoRepository.findAll();

        // Comprobacion de la existencia de los datos y sus valores.
        assertThat(listaPedidos).isNotEmpty();
        assertThat(assertThat(listaPedidos.get(0).getId_usuario()).isEqualTo(11L));
        assertThat(assertThat(listaPedidos.get(1).getId_usuario()).isEqualTo(10L));
    }

    // Registro y busqueda de usuario por su ID
    @Test
    @DisplayName("Busqueda de un pedido segun su ID de usuario")
    void buscarPedidoPorUsuario() {
        Pedido pedido = new Pedido();
        pedido.setFecha_pedido(LocalDate.of(2025, 6, 22));
        pedido.setEstado(2);
        pedido.setId_usuario(22L);

        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(6);
        detalle.setId_producto(7L);
        detalle.setPedido(pedido);

        pedido.getDetalles().add(detalle);

        pedidoRepository.save(pedido);

        List<Pedido> pedidosUsuario = pedidoRepository.findById_usuario(22L);

        assertThat(pedidosUsuario).isNotEmpty();
        assertThat(pedidosUsuario.get(0).getId_usuario()).isEqualTo(22L);
    }

    /*Se comprueba que al eliminar un pedido, se eliminan tambien sus detalles por
    la anotación cascade, en la clase pedido. */
    @Test
    @DisplayName("Eliminación de un pedido creado")
    void eliminarPedidoConDetalles() {
        Pedido pedido = new Pedido();
        pedido.setFecha_pedido(LocalDate.of(2025, 6, 22));
        pedido.setEstado(2);
        pedido.setId_usuario(22L);

        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(6);
        detalle.setId_producto(7L);
        detalle.setPedido(pedido);

        pedido.getDetalles().add(detalle);

        Pedido guardado = pedidoRepository.save(pedido);

        pedidoRepository.delete(guardado);

        assertThat(pedidoRepository.findById(guardado.getId())).isEmpty();
    }

}
