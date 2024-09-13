package com.example.demo.repository;

import com.example.demo.entity.FacturaProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaProductoRepository extends JpaRepository<FacturaProducto,Long> {
}
