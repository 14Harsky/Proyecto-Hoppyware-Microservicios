package com.usuarios_hw.MS.Usuarios.HW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import com.usuarios_hw.MS.Usuarios.HW.assemblers.UsuarioModelAssembler;
import com.usuarios_hw.MS.Usuarios.HW.model.Usuario;
import com.usuarios_hw.MS.Usuarios.HW.service.UsuarioService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/hoppyware/v2/usuario")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios, linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return assembler.toModel(usuario);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE, consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Usuario a actualizar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class), examples = @ExampleObject(value = "{\"id\": \"1\", \"nombre\": \"Nuevo usuario\", \"run\": \"12345678\",\"dvrun\": \"K\", \"pnombre\": \"Benito\", \"snombre\": \"Rey\", \"appaterno\": \"Lopez\", \"apmaterno\": \"Ruiz\", \"correo\": \"hola@hola.cl\", \"num_telefono\": \"9999999\", \"fecha_nacto\": \"12-03-1990\"}"))) @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario existente = usuarioService.findById(id);

            if (!usuarioActualizado.getId().equals(id)) {
                return ResponseEntity.badRequest().build(); 
            }

            existente.setRun(usuarioActualizado.getRun());
            existente.setDvrun(usuarioActualizado.getDvrun());
            existente.setPnombre(usuarioActualizado.getPnombre());
            existente.setSnombre(usuarioActualizado.getSnombre());
            existente.setAppaterno(usuarioActualizado.getAppaterno());
            existente.setApmaterno(usuarioActualizado.getApmaterno());
            existente.setFecha_nacto(usuarioActualizado.getFecha_nacto());
            existente.setCorreo(usuarioActualizado.getCorreo());
            existente.setNum_telefono(usuarioActualizado.getNum_telefono());

            Usuario actualizado = usuarioService.save(existente);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
