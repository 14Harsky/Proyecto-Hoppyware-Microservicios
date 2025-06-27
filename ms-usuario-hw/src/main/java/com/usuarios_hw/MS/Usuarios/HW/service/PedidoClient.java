package com.usuarios_hw.MS.Usuarios.HW.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

import com.usuarios_hw.MS.Usuarios.HW.dto.*;


@FeignClient(name = "pedido-service", url= "${pedido-service.url}")
public interface PedidoClient {

    @GetMapping("/{id}/cliente-pedidos")
    List<PedidoDto> getPedidoPorUsr(@PathVariable("id") Long id);


    
}
