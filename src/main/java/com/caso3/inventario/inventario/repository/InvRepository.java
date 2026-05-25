package com.caso3.inventario.inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caso3.inventario.inventario.model.Producto;


@Repository
public interface InvRepository extends JpaRepository<Producto, Long> {

}