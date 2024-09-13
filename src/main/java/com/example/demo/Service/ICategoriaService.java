package com.example.demo.Service;

import com.example.demo.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    Categoria guardarCategoria(Categoria categoria);

    void editarCategoria(Categoria categoria);

    Optional<Categoria> buscarPorIdCategoria(Long id);

    List<Categoria> buscarTodosLasCategorias();

    void eliminarCategoria(Long id);
}
