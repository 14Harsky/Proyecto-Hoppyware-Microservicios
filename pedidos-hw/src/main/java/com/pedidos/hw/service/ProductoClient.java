package com.pedidos.hw.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pedidos.hw.dto.ProductoDTO;

@FeignClient(name = "producto-service", url = "http://localhost:8082/hoppyware/v1/producto")
public interface ProductoClient {

    @GetMapping("/{id}")
    ProductoDTO getProductoDTO(@PathVariable("id") Long id);

    @PutMapping("/{id}/stock")
    void descontarStock(@PathVariable("id") Long id, @RequestParam("cantidadComprada") Integer cantidad);
}
