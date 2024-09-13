package com.example.demo.Service;

import com.example.demo.entity.Factura;
import com.example.demo.entity.Producto;
import com.example.demo.entity.User;

import java.util.List;

public interface IFacturaService {
    Factura facturar(List<Producto> p, Long userId);
}
