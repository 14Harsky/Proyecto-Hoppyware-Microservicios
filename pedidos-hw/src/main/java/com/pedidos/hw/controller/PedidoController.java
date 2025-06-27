package com.pedidos.hw.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pedidos.hw.dto.PedidoUsuarioDTO;
import com.pedidos.hw.dto.ProductoDTO;
import com.pedidos.hw.dto.UsuarioDto;
import com.pedidos.hw.model.DetallePedido;
import com.pedidos.hw.model.Pedido;
import com.pedidos.hw.service.PedidoService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(info = @Info(title = "Pedidos Hoppyware", description = "Endpoints para microservicio de Pedidos.", version = "v1"), tags = {
        @Tag(name = "Metodos GET", description = "Obtienen recursos"),
        @Tag(name = "Metodos POST", description = "Almacenan recursos"),
        @Tag(name = "Metodo DELETE", description = "Elimina un recurso "),
        @Tag(name = "Metodo PUT", description = "Actualiza un recurso")
})
// Controlador REST para definir los distintos endpoints del microservicio de
// Pedidos. Se migro de un resttemplate a Feign Client
@RestController
@RequestMapping("/hoppyware/v1/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Mapeo de datos de contacto segun ID de pedido llamando un DTO para recibir
    // datos del MS de usuarios
    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene los datos de contacto del usuario asignado al pedido", responses = {
            @ApiResponse(responseCode = "200", description = "Datos del usuario encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class), examples = @ExampleObject(name = "Ejemplo de UsuarioDto", value = "{\n"
                    +
                    "  \"id\": 11,\n" +
                    "  \"nombre\": \"Carlos\",\n" +
                    "  \"apellido\": \"González\",\n" +
                    "  \"email\": \"carlos@example.com\",\n" +
                    "  \"telefono\": \"+56912345678\"\n" +
                    "}")))
    })
    @GetMapping("/{idPedido}/cliente-contacto")
    public ResponseEntity<UsuarioDto> getContactoUsuario(@PathVariable Long idPedido) {
        UsuarioDto contacto = pedidoService.getContactoPorIdPedido(idPedido);
        return ResponseEntity.ok(contacto);
    }

    // Listado de pedidos usando el metodo findAll del Repository
    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene un listado de todos los pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoUsuarioDTO>> listarPedidos() {
        List<PedidoUsuarioDTO> pedido = pedidoService.listarPedidosDTO();
        if (pedido.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedido);

    }

    // Guardado de datos de un Pedido
    @Tag(name = "Metodos POST")
    @Operation(summary = "Almacena el cuerpo de UN pedido")
    @PostMapping("/guardar")
    @ApiResponse(responseCode = "201", description = "Pedido registrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class, description = "Objeto Pedido con lista de detalles"), examples = @ExampleObject(name = "Ejemplo de Pedido", value = "{\n"
            +
            "  \"fecha_pedido\": \"22-06-2025\",\n" +
            "  \"estado\": 2,\n" +
            "  \"id_usuario\": 11,\n" +
            "  \"detalles\": [\n" +
            "    {\n" +
            "      \"cantidad\": 6,\n" +
            "      \"id_producto\": 8\n" +
            "    },\n" +
            "    {\n" +
            "      \"cantidad\": 4,\n" +
            "      \"id_producto\": 2\n" +
            "    }\n" +
            "  ]\n" +
            "}")))
    public ResponseEntity<Pedido> guardar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Pedido a guardar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class), examples = @ExampleObject(value = "{\n"
                    +
                    "  \"fecha_pedido\": \"22-06-2025\",\n" +
                    "  \"estado\": 2,\n" +
                    "  \"id_usuario\": 11,\n" +
                    "  \"detalles\": [\n" +
                    "    {\n" +
                    "      \"cantidad\": 6,\n" +
                    "      \"id_producto\": 8\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"cantidad\": 4,\n" +
                    "      \"id_producto\": 2\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}"))) @RequestBody Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setPedido(pedido);

            System.out.println("DEBUG - ID PRODUCTO: " + detalle.getId_producto());
        }
        Pedido nuevoPedido = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    // Guardado de una lista
    @Tag(name = "Metodos POST")
    @Operation(summary = "Almacena un arreglo de pedidos")
    @PostMapping("/guardarLista")
    public ResponseEntity<List<Pedido>> guardarLista(@RequestBody List<Pedido> listaPedidos) {
        for (Pedido pedido : listaPedidos) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                detalle.setPedido(pedido);
                System.out.println("DEBUG - ID PRODUCTO: " + detalle.getId_producto());
            }
        }
        List<Pedido> nuevosPedidos = pedidoService.saveLista(listaPedidos);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevosPedidos);
    }

    // Busqueda de pedidos por ID del pedido aceptando una variable dentro de la URL
    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene  informacion de un pedido segun ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarId(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Busqueda de pedidos por fecha del pedido
    @Tag(name = "Metodos GET")
    @Operation(summary = "Obtiene  una lista de pedidos segun fecha")
    @GetMapping("/buscarFecha/{fecha_pedido}")
    public ResponseEntity<List<Pedido>> buscarFecha(
            @PathVariable @DateTimeFormat(pattern = "dd-MM-yy") LocalDate fecha_pedido) {
        List<Pedido> pedidos = pedidoService.buscarFecha(fecha_pedido);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    // Solicitud POST para actualizar un pedido cambiando todo sus atributos
    @Tag(name = "Metodo PUT")
    @Operation(summary = "Actualización de un pedido segun su ID")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        try {
            Pedido ped = pedidoService.findById(id);
            ped.setId(pedido.getId());
            ped.setEstado(pedido.getEstado());
            ped.setFecha_pedido(pedido.getFecha_pedido());
            ped.setId(pedido.getId());
            ped.setId_usuario(pedido.getId_usuario());

            pedidoService.save(ped);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminacion de pedidos por su ID de pedido usando una solicitud DELETE
    @Tag(name = "Metodo DELETE")
    @Operation(summary = "Elimina un pedido de la base de datos usando su ID")
    @ApiResponse(responseCode = "200", description = "Pedido eliminado")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Busqueda de pedidos por la ID de usuario comunicandose con el microservicio
    // de usuarios
    @Tag(name = "Metodos GET")
    @Tag(name = "Integracion microservicio Usuarios")
    @Operation(summary = "Obtiene los pedidos asociados a un cliente", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos del usuario", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoUsuarioDTO.class), examples = @ExampleObject(name = "Ejemplo PedidoUsuarioDTO", value = "[{\n"
                    +
                    "  \"id\": 4,\n" +
                    "  \"fecha_pedido\": \"22-06-2025\",\n" +
                    "  \"estado\": 1,\n" +
                    "  \"usuario\": {\n" +
                    "    \"id\": 11,\n" +
                    "    \"nombre\": \"Carlos\"\n" +
                    "  }\n" +
                    "}]")))
    })
    @GetMapping("/{id}/cliente-pedidos")
    public ResponseEntity<List<PedidoUsuarioDTO>> getPedidosUsuario(@PathVariable Long id) {
        try {
            List<PedidoUsuarioDTO> pedidosPorUser = pedidoService.listarPedidosPorUsuario(id);
            return ResponseEntity.ok(pedidosPorUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @Tag(name = "Metodos GET")
    @Tag(name = "Integracion microservicio Productos")
    @Operation(summary = "Obtiene información de un producto usando su ID", responses = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDTO.class), examples = @ExampleObject(name = "Ejemplo ProductoDTO", value = "{\n"
                    +
                    "  \"id\": 8,\n" +
                    "  \"nombre\": \"Malta Pilsner\",\n" +
                    "  \"precio\": 12000,\n" +
                    "  \"stock\": 100\n" +
                    "}")))
    })
    @GetMapping("/{id}/producto-id")
    public ResponseEntity<ProductoDTO> getProductoPorId(@PathVariable Long id) {
        try {
            ProductoDTO producto = pedidoService.getProductoPorId(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
