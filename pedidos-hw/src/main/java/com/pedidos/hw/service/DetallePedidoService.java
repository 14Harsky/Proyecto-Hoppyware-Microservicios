package com.pedidos.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pedidos.hw.model.*;
//import com.pedidos.hw.service.ProductoClient;

import com.pedidos.hw.repository.DetallePedidoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DetallePedidoService {

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    public DetallePedido save(DetallePedido detalles){
        return detallePedidoRepository.save(detalles);
    }
}
