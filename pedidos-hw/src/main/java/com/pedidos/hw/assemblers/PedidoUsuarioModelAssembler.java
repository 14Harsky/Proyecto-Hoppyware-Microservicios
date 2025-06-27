package com.pedidos.hw.assemblers;

import com.pedidos.hw.controller.PedidoControllerV2;
import com.pedidos.hw.dto.PedidoUsuarioDTO;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PedidoUsuarioModelAssembler implements RepresentationModelAssembler<PedidoUsuarioDTO, EntityModel<PedidoUsuarioDTO>> {

    @Override
    public EntityModel<PedidoUsuarioDTO> toModel(PedidoUsuarioDTO dto) {
        dto.getDetalles().forEach(detalle -> {
            Link productoLink = Link.of("http://localhost:8082/hoppyware/v1/producto/" + detalle.getId_producto())
            .withRel("producto");
            detalle.add(productoLink);
        });

        // Enlaces principales del pedido
        return EntityModel.of(dto,
            linkTo(methodOn(PedidoControllerV2.class).buscarId(dto.getId())).withSelfRel(),
            linkTo(methodOn(PedidoControllerV2.class).listarPedidos()).withRel("todos"),
            linkTo(methodOn(PedidoControllerV2.class).getContactoUsuario(dto.getId())).withRel("usuario")
        );
    }
}
