package com.pedidos.hw.repository;

import com.pedidos.hw.model.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Query nativa de SQL para filtrar pedidos por fecha, utilizados en un metodo del controlador
    @Query(value = "SELECT * FROM pedido WHERE fecha_pedido LIKE TO_DATE(:fecha_pedido)", nativeQuery = true)
    List<Pedido> findAllByFecha_pedido(LocalDate fecha_pedido);

    // Query nativa de SQL para encontrar pedidos segun la id de usuario. Los campos deben coincidir con los definidos en la clase Pedido
    @Query(value = "SELECT * FROM pedido WHERE id_usuario = :id_usuario", nativeQuery = true)
    List<Pedido> findById_usuario(Long id_usuario);


}   
