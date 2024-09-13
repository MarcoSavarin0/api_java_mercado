package com.example.demo.Service;

import com.example.demo.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    Producto guardarProducto(Producto producto);

   List<Producto> buscarTodosLosProductos();

   Optional<Producto> buscarPorIdProducto(Long id);

    void editarProducto(Producto producto);

    void eliminarProducto(Long id);

}
