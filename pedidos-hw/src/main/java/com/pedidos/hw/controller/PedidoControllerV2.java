package com.pedidos.hw.controller;

import com.pedidos.hw.assemblers.PedidoUsuarioModelAssembler;
import com.pedidos.hw.dto.PedidoUsuarioDTO;
import com.pedidos.hw.dto.UsuarioDto;
import com.pedidos.hw.service.PedidoService;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/hoppyware/v2/pedido")
@RequiredArgsConstructor
public class PedidoControllerV2 {

    private final PedidoService pedidoService;
    private final PedidoUsuarioModelAssembler assembler;

    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene un listado de todos los pedidos con enlaces HATEOAS")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PedidoUsuarioDTO>>> listarPedidos() {
        List<PedidoUsuarioDTO> lista = pedidoService.listarPedidosDTO();

        List<EntityModel<PedidoUsuarioDTO>> modelos = lista.stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(modelos,
                linkTo(methodOn(PedidoControllerV2.class).listarPedidos()).withSelfRel())
        );
    }

    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene información de un pedido según ID con enlaces HATEOAS")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PedidoUsuarioDTO>> buscarId(@PathVariable Long id) {
        try {
            PedidoUsuarioDTO dto = pedidoService.listarPedidosDTO()
                                                .stream()
                                                .filter(p -> p.getId().equals(id))
                                                .findFirst()
                                                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene los datos de contacto del usuario asignado al pedido")
    @GetMapping("/{id}/cliente-contacto")
    public ResponseEntity<UsuarioDto> getContactoUsuario(@PathVariable Long id) {
        UsuarioDto contacto = pedidoService.getContactoPorIdPedido(id);
        return ResponseEntity.ok(contacto);
    }
}
