package com.usuarios_hw.MS.Usuarios.HW.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.usuarios_hw.MS.Usuarios.HW.dto.PedidoDto;
import com.usuarios_hw.MS.Usuarios.HW.model.Usuario;
import com.usuarios_hw.MS.Usuarios.HW.service.UsuarioService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

// Controlador REST para definir endpoints del microservicio
// El uso de ResponseEntity afina las respuestas de estado HTTP
@OpenAPIDefinition( 
    info = @Info(title = "Usuarios Hoppyware", description = "Endpoints para microservicio de Usuarios.", version = "v1"),
    tags = {
    @Tag(name = "Metodos GET", description = "Obtienen recursos"),
    @Tag(name = "Metodos POST", description = "Almacenan recursos"),
    @Tag(name = "Metodo DELETE", description = "Elimina un recurso "),
    @Tag(name = "Metodo PUT", description = "Actualiza un recurso")
}

)
@RestController
@RequestMapping("/hoppyware/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Listado de todos los usuarios
    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene un listado de todos los usuarios")
    @GetMapping()
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    // Busqueda de Usuarios por su ID como variable en la URL
    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene  informacion de un usuario segun ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> busquedaPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Guardado de usuarios en un cuerpo de JSON con mas de un elemento
    @Tag(name = "Metodos POST")
    @Operation(summary = "Almacena un arreglo de uno o más usuarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
        content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class),
        examples = @ExampleObject(value = "{\"id\": \"1\", \"nombre\": \"Nuevo usuario\", \"run\": \"12345678\",\"dvrun\": \"K\", \"pnombre\": \"Benito\", \"snombre\": \"Rey\", \"appaterno\": \"Lopez\", \"apmaterno\": \"Ruiz\", \"correo\": \"hola@hola.cl\", \"num_telefono\": \"9999999\", \"fecha_nacto\": \"12-03-1990\"}")
        )}),
        @ApiResponse(responseCode = "400", description = "Ingreso inválido")
    })
    @PostMapping("/guardarLista")
    public ResponseEntity<List<Usuario>> guardar(@io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Usuario a guardar", required = true,
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class),
        examples = @ExampleObject(value = "{\"nombre\": \"Nuevo usuario\", \"run\": \"12345678\",\"dvrun\": \"K\", \"pnombre\": \"Benito\", \"snombre\": \"Rey\", \"appaterno\": \"Lopez\", \"apmaterno\": \"Ruiz\", \"correo\": \"hola@hola.cl\", \"num_telefono\": \"9999999\", \"fecha_nacto\": \"12-03-1990\"}"))
    )
        @RequestBody List<Usuario> listaUsuarios) {
        List<Usuario> nuevaLista = usuarioService.saveLista(listaUsuarios);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaLista);
        // Usar HttpStatus.CREATED devolvera un codigo 201 que indica la creacion de un nuevo elemento
    }

    // Guardado de usuario cuando el cuerpo contiene solo un usuario
    @Tag(name = "Metodos POST")
    @Operation(summary = "Almacena solo UN usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente",
        content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class),
        examples = @ExampleObject(value = "{\"id\": \"1\", \"nombre\": \"Nuevo usuario\", \"run\": \"12345678\",\"dvrun\": \"K\", \"pnombre\": \"Benito\", \"snombre\": \"Rey\", \"appaterno\": \"Lopez\", \"apmaterno\": \"Ruiz\", \"correo\": \"hola@hola.cl\", \"num_telefono\": \"9999999\", \"fecha_nacto\": \"12-03-1990\"}")
        )}),
        @ApiResponse(responseCode = "400", description = "Ingreso inválido")
    })
    @PostMapping("/guardar")
    public ResponseEntity<Usuario> guardarUsr(@io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Usuario a guardar", required = true,
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class),
        examples = @ExampleObject(value = "{\"nombre\": \"Nuevo usuario\", \"run\": \"12345678\",\"dvrun\": \"K\", \"pnombre\": \"Benito\", \"snombre\": \"Rey\", \"appaterno\": \"Lopez\", \"apmaterno\": \"Ruiz\", \"correo\": \"hola@hola.cl\", \"num_telefono\": \"9999999\", \"fecha_nacto\": \"12-03-1990\"}"))
    )@RequestBody Usuario usuario) {
        Usuario newUsr = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUsr);
    }

    // Actualizacion de los campos de un usuario
    @Tag(name = "Metodo PUT")
    @Operation(summary = "Actualización de un usuario segun su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
        content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = Usuario.class),
        examples = @ExampleObject(value = "{\"id\": \"1\", \"nombre\": \"Nuevo usuario\", \"run\": \"12345678\",\"dvrun\": \"K\", \"pnombre\": \"Benito\", \"snombre\": \"Rey\", \"appaterno\": \"Lopez\", \"apmaterno\": \"Ruiz\", \"correo\": \"hola@hola.cl\", \"num_telefono\": \"9999999\", \"fecha_nacto\": \"12-03-1990\"}")
        )}),
        @ApiResponse(responseCode = "400", description = "Ingreso inválido")
    })
    @PutMapping("/actualizar/{id}")
public ResponseEntity<Usuario> actualizar(
        @PathVariable Long id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Usuario a actualizar", required = true,
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Usuario.class),
                        examples = @ExampleObject(value = "{\"id\": \"1\", \"nombre\": \"Nuevo usuario\", \"run\": \"12345678\",\"dvrun\": \"K\", \"pnombre\": \"Benito\", \"snombre\": \"Rey\", \"appaterno\": \"Lopez\", \"apmaterno\": \"Ruiz\", \"correo\": \"hola@hola.cl\", \"num_telefono\": \"9999999\", \"fecha_nacto\": \"12-03-1990\"}"))
        )
        @RequestBody Usuario usuario) {

    try {
        Usuario usr = usuarioService.findById(id);

        // Validación opcional: que el ID en el path coincida con el del body (por seguridad)
        if (!usuario.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        // Copiar propiedades actualizables
        usr.setRun(usuario.getRun());
        usr.setDvrun(usuario.getDvrun());
        usr.setPnombre(usuario.getPnombre());
        usr.setSnombre(usuario.getSnombre());
        usr.setAppaterno(usuario.getAppaterno());
        usr.setApmaterno(usuario.getApmaterno());
        usr.setFecha_nacto(usuario.getFecha_nacto());
        usr.setCorreo(usuario.getCorreo());
        usr.setNum_telefono(usuario.getNum_telefono());

        Usuario actualizado = usuarioService.save(usr);
        return ResponseEntity.ok(actualizado);

    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}

    // Busqueda de usuarios por RUN, devuelve solo un resultado ya que no deben existir registros de RUT repetidos
    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene información de un usuario con su RUN")
    @GetMapping("/{run}/usuario-run")
    public ResponseEntity<Usuario> buscarRun(@PathVariable String run) {
        try {
            Usuario usuario = usuarioService.findByRun(run);
            return ResponseEntity.ok(usuario);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminacion de usuarios por su ID
    @Tag(name = "Metodo DELETE")
    @Operation(summary = "Elimina un usuario de la base de datos usando su ID")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado")
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    // Busqueda de pedidos por id de usuario, comunicacion a traves de Feign Client
    @Tag(name = "Metodos GET")
    @Tag(name = "Integracion microservicio Pedidos")
    @Operation(summary = "Obtiene los pedidos asociados a un cliente")
    @GetMapping("/{id}/pedidos-cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud exitosa",
        content = {@Content(mediaType = "application/json",
        schema = @Schema(implementation = PedidoDto.class),
        examples = @ExampleObject(value = "{\"id\": \"2\", \"fecha_pedido\": \"14-05-2025\", \"estado\": \"Recibido\", \"costoTotal\": \"15990\", \"detalles\": {\"cantidad\": \"2\"}, \"producto\": {\"nombre\": \"Kit de fermentación\", \"precio\": \"15990\", \"origen\": \"Chile\"}}")
        )}),
        @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<PedidoDto>> getPedidosUsuario(@PathVariable Long id) {
        try {
            List<PedidoDto> pedidosPorUsr = usuarioService.getPedidoPorIdUsr(id);
            return ResponseEntity.ok(pedidosPorUsr);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
