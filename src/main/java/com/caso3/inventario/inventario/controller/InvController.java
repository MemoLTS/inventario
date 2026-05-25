package com.caso3.inventario.inventario.controller;

import java.util.List;

import jakarta.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.caso3.inventario.inventario.model.Producto;
import com.caso3.inventario.inventario.service.InvService;

@RestController
@RequestMapping("/api/v1/ecomarket/inventario")
public class InvController {

    @Autowired
    private InvService invservice;

    // Obtener todos los productos
    @GetMapping("/productos")
    public List<Producto> getProductos() {
        return invservice.readAllProd();
    }

    // Seed de productos
    @GetMapping("/seedprod")
    public ResponseEntity<?> seedprod() {
        invservice.seedprod();
        return ResponseEntity.status(HttpStatus.OK)
                .body("Productos cargados correctamente");
    }

    // Agregar producto
    @PostMapping("/addprod")
    public ResponseEntity<?> postProducto(
            @Valid @RequestBody Producto producto) {

        Producto prod = invservice.register(producto);

        if (prod != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Producto registrado");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al registrar producto");
    }

    // Buscar producto por ID
    @GetMapping("/productos/{id}")
    public ResponseEntity<?> getProducto(@PathVariable Long id) {

        List<Producto> productos = invservice.readAllProd();

        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(producto);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Producto no encontrado");
    }

    // Actualizar producto
    @PutMapping("/updateprod/{id}")
    public ResponseEntity<?> updateProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {

        List<Producto> productos = invservice.readAllProd();

        for (Producto p : productos) {
            if (p.getId().equals(id)) {

                p.setNombre(producto.getNombre());
                p.setPrecio(producto.getPrecio());

                invservice.register(p);

                return ResponseEntity.status(HttpStatus.OK)
                        .body("Producto actualizado");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Producto no encontrado");
    }

    @PutMapping("/updateprod/{id}/stock")
    public ResponseEntity<?> updateStock(
            @PathVariable Long id,
            @RequestBody int stock) {
        List<Producto> productos = invservice.readAllProd();
        for (Producto p : productos) {
            if (p.getId().equals(id)) {

                p.setStock(stock);

                invservice.register(p);

                return ResponseEntity.status(HttpStatus.OK)
                        .body("Stock actualizado");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Producto no encontrado");
    }

    // Eliminar producto
    @DeleteMapping("/deleteprod/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable Long id) {

        List<Producto> productos = invservice.readAllProd();

        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {

                productos.remove(producto);

                return ResponseEntity.status(HttpStatus.OK)
                        .body("Producto eliminado");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Producto no encontrado");
    }
}