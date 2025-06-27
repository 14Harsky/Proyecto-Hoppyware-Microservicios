package com.hoppyware.productos;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.hoppyware.productos.model.Producto;
import com.hoppyware.productos.repository.ProductoRepository;

@DataJpaTest
@ActiveProfiles("test")
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void contextoLoads() {
        assertThat(productoRepository).isNotNull();
    }
    
    @Test
    void verificar() {
        System.out.println("Productos existentes: " + productoRepository.findAll());
    }

    @Test
    @DisplayName("Agregar un producto para testear la DB")
    void agregarProducto() {

        Producto producto = new Producto();

        producto.setNombre("Cerveza Test");
        producto.setDescripcion("Cerveza de prueba");
        producto.setProveedor("Proveedor Test");
        producto.setPrecio(9990);
        producto.setStock(50);
        producto.setOrigen("Testlandia");

        Producto productoGuardado = productoRepository.save(producto);

        assertNotNull(productoGuardado.getId());
    }

    @Test
    @DisplayName("Test para guardado de lista de productos")
    void guardarListaProductos() {

        Producto producto1 = new Producto();

        producto1.setNombre("Cerveza Test");
        producto1.setDescripcion("Cerveza de prueba");
        producto1.setProveedor("Proveedor Test");
        producto1.setPrecio(9990);
        producto1.setStock(50);
        producto1.setOrigen("Testlandia");

        Producto producto2 = new Producto();

        producto2.setNombre("Cerveza Testera");
        producto2.setDescripcion("Cerveza de Testeo");
        producto2.setProveedor("Proveedor Testeo");
        producto2.setPrecio(7990);
        producto2.setStock(30);
        producto2.setOrigen("Testeria");

        productoRepository.save(producto1);
        productoRepository.save(producto2);

        List<Producto> listaProductos = List.of(producto1, producto2);

        assertThat(listaProductos).isNotEmpty();
        assertThat(listaProductos.get(0).getNombre().equals(producto1.getNombre()));
        assertThat(listaProductos.get(1).getNombre().equals(producto2.getNombre()));
    }
    
    @Test
    @DisplayName("Buacar un producto por su proveedor")
    void buscarPorProveedor() {

        Producto producto = new Producto();

        producto.setNombre("Cerveza Test");
        producto.setDescripcion("Cerveza de prueba");
        producto.setProveedor("Proveedor Test");
        producto.setPrecio(100);
        producto.setStock(50);
        producto.setOrigen("Testlandia");

        productoRepository.save(producto);

        assertThat(productoRepository.findByProveedor("Proveedor Test")).isNotNull();

        }

    @Test
    @DisplayName("Eliminar un producto")
    void eliminarProducto() {

        Producto producto = new Producto();

        producto.setNombre("Cerveza Test");
        producto.setDescripcion("Cerveza de prueba");
        producto.setProveedor("Proveedor Test");
        producto.setPrecio(9990);
        producto.setStock(50);
        producto.setOrigen("Testlandia");

        Producto productoGuardado = productoRepository.save(producto);

        productoRepository.delete(productoGuardado);

        assertThat(productoRepository.findById(productoGuardado.getId())).isEmpty();
    }
}