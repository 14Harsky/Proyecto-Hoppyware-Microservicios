package com.hoppyware.productos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.hoppyware.productos.model.*;
import com.hoppyware.productos.repository.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductoService {

    public ProductoService(ProductoRepository productoRepository1) {
    }

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    public Producto findById(Long id){
        return productoRepository.findById(id).get();
    }

    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    public List<Producto> saveLista(List<Producto> listaProductos){
        return productoRepository.saveAll(listaProductos);
    }

    public List<Producto> buscarPorProveedor(String proveedor){
        return productoRepository.findByProveedor(proveedor);
    }
    
    public void delete(Long id){
        productoRepository.deleteById(id);
    }

}
