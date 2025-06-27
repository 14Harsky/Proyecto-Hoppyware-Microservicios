package com.hoppyware.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hoppyware.productos.model.*;
import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long>{

    @Query(value = "SELECT * FROM producto WHERE proveedor = :proveedor", nativeQuery = true)
    List<Producto> findByProveedor(String proveedor);
}
