package com.hoppyware.productos.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hoppyware.productos.model.Producto;
import com.hoppyware.productos.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/hoppyware/v1/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

@Tag(name = "Metodos GET", description = "Operaciones para obtener productos")

    @Operation(summary= "Listar Productos", description = "Lista todos los productos disponibles")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        List<Producto> listaProductos = productoService.findAll();
        if (listaProductos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaProductos);
    }

@Tag(name = "Metodos GET", description = "Operaciones para obtener productos")

    @Operation(summary = "Buscar Producto por ID", description = "Busca un producto por su ID")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @Parameter(name = "id", description = "ID del producto a buscar", required = true, example = "9")
        
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarId(@PathVariable Long id) {
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

@Tag(name = "Metodos POST", description = "Operaciones para crear o guardar productos")

    @Operation(summary = "Guardar Producto", description = "Guarda un producto o una lista de productos")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @PostMapping("/guardar")
    public ResponseEntity<List<Producto>> guardar(@RequestBody List<Producto> listaProductos) {
        List<Producto> listaProds = productoService.saveLista(listaProductos);
        return ResponseEntity.status(HttpStatus.CREATED).body(listaProds);
    }

@Tag(name = "Metodos DELETE", description = "Operaciones para eliminar productos")

    @Operation(summary = "Eliminar Producto", description = "Elimina un producto por su ID")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id){
        try{
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

@Tag(name = "Metodos PUT", description = "Operaciones para actualizar productos")

    @Operation(summary = "Actualizar Stock", description = "Actualiza el stock de un producto")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, @RequestParam Integer cantidadComprada) {
        try {
            Producto producto = productoService.findById(id);
            if (producto.getStock() < cantidadComprada) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
            }
            producto.setStock(producto.getStock() - cantidadComprada);
            productoService.save(producto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
