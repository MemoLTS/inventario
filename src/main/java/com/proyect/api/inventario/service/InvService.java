package com.proyect.api.inventario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.api.inventario.model.Producto;
import com.proyect.api.inventario.repository.InvRepository;

@Service
public class InvService {

    @Autowired
    private InvRepository invRepository;

    public List<Producto> readAllProd() {
        return invRepository.findAll();
    }

    public Producto register(Producto producto) {
        return invRepository.save(producto);
    }

    public Producto updateStock(Long id, int cantidad) {
        Producto producto = invRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setStock(cantidad);
            return invRepository.save(producto);
        }
        return null;
    }
    public void seedprod() {

        List<Producto> productos = new ArrayList<>();

        Producto p1 = new Producto();
        p1.setNombre("Papas Lays");
        p1.setPrecio(1990.0);
        p1.setStock(50);

        Producto p2 = new Producto();
        p2.setNombre("cocacola");
        p2.setPrecio(1590.0);
        p2.setStock(100);

        Producto p3 = new Producto();
        p3.setNombre("monster energy");
        p3.setPrecio(1890.0);
        p3.setStock(30);

        productos.add(p1);
        productos.add(p2);
        productos.add(p3);

        invRepository.saveAll(productos);
    }
}