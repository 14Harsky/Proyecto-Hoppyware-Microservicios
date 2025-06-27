package com.pedidos.hw.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.pedidos.hw.dto.UsuarioDto;

// Cliente que establece y define la comunicación con el microservicio de usuarios a través del puerto 8081 definido en las propiedades de cada MS
@FeignClient(name = "usuario-service", url = "http://localhost:8080/hoppyware/v1/usuario")
public interface UsuarioClient {

    // Busqueda de usuario por RUN trayendo sus datos a traves del DTO (Objeto de transferencia de datos)
    @GetMapping("/usuario/buscarRun")
    UsuarioDto getUsuarioDto(@PathVariable String run);

    // Busqueda de usuario por su ID propia
    @GetMapping("/{id}")
    UsuarioDto getUsrPorId(@PathVariable("id") Long id);

   
}   
