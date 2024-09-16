package com.example.demo.Service.implement;

import com.example.demo.Service.ICategoriaService;
import com.example.demo.entity.Categoria;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ICategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriaService implements ICategoriaService {
    private final ICategoriaRepository categoriaRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    public CategoriaService(ICategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        try {
            Categoria categoriaGuardar = categoriaRepository.save(categoria);
            logger.info("Categoria Guardada " + categoria);
            return categoriaGuardar;
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Datos inválidos: no se pudo crear la CATEGORIA" + e);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error inesperado al crear la CATEGORIA", e);
        }
    }

    @Override
    public void editarCategoria(Categoria categoria) {
        Optional<Categoria> categoriaMatch = categoriaRepository.findById(categoria.getId());
        if (categoriaMatch.isEmpty()) {
            logger.info("No se encontró la categoria con id: " + categoria.getId());
            throw new ResourceNotFoundException("No se encontró la categoria con id: " + categoria.getId());
        }
        categoriaRepository.save(categoria);
        logger.info("categoria actualizada con éxito: " + categoria);
    }

    @Override
    public Optional<Categoria> buscarPorIdCategoria(Long id) {
        Optional<Categoria> categoriaMatch = categoriaRepository.findById(id);
        if (categoriaMatch.isEmpty()) {
            logger.info("No se encontró la categoria con id: " + id);
            throw new ResourceNotFoundException("No se encontró la categoria con id: " + id);
        }
        logger.info("Se encontro la categoria con el id " + id + " " + categoriaMatch);
        return categoriaMatch;
    }

    @Override
    public List<Categoria> buscarTodosLasCategorias() {
        try {
            List<Categoria> listaDeCategorias = categoriaRepository.findAll();
            logger.info("Se pidio la lista de categorias ", listaDeCategorias);
            return listaDeCategorias;
        } catch (Exception e) {
            logger.error("Error al buscar todas las categorias", e);
            throw new InternalServerErrorException("Error al obtener la lista de categorias", e);
        }
    }

    @Override
    public void eliminarCategoria(Long id) {
        Optional<Categoria> categoriaMatch = categoriaRepository.findById(id);
        if (categoriaMatch.isEmpty()) {
            logger.info("No se encontro ninguna categoria con ese Id " + id);
            throw new BadRequestException("Error el eliminar la categoria con el id " + id);
        } else {
            categoriaRepository.deleteById(id);

        }
    }
}
