package com.pedidos.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedidos.hw.model.*;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

}
