package com.proyect.api.inventario.repository;

import com.proyect.api.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvRepository extends JpaRepository<Producto, Long> {

}