package com.example.demo.Service.implement;

import com.example.demo.Service.IProductoService;
import com.example.demo.entity.Producto;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerErrorException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.IProductoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductoService implements IProductoService {
    private final IProductoRepository productoRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductoService.class);


    public ProductoService(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        try {
            Producto productoAguardar = productoRepository.save(producto);
            logger.info("Producto Guardado " + productoAguardar);
            return productoAguardar;
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Datos inválidos: no se pudo crear el producto" + e);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error inesperado al crear el producto", e);
        }
    }

    @Override
    public List<Producto> buscarTodosLosProductos() {
        try {
            List<Producto> listProductos = productoRepository.findAll();
            logger.info("Todos los productos pedidos: " + listProductos);
            return listProductos;
        } catch (Exception e) {
            logger.error("Error al buscar todos los productos", e);
            throw new InternalServerErrorException("Error al obtener la lista de productos", e);
        }
    }

    @Override
    public Optional<Producto> buscarPorIdProducto(Long id) {
        Optional<Producto> productoMatch = productoRepository.findById(id);
        if (productoMatch.isEmpty()) {
            logger.info("No se encontró el producto con el id " + id);
            throw new ResourceNotFoundException("Producto no encontrado con el id: " + id);
        }
        logger.info("Se encontró el producto con el id " + id + ": " + productoMatch);
        return productoMatch;
    }

    @Override
    public void editarProducto(Producto producto) {
        Optional<Producto> productoMatch = productoRepository.findById(producto.getId());
        if (productoMatch.isEmpty()) {
            logger.info("No se encontró el producto con id: " + producto.getId());
            throw new ResourceNotFoundException("No se encontró el producto con id: " + producto.getId());
        }
        productoRepository.save(producto);
        logger.info("Producto actualizado con éxito: " + producto);

    }

    @Override
    public void eliminarProducto(Long id) {
        Optional<Producto> productoMatch = productoRepository.findById(id);
        if (!productoMatch.isPresent()) {
            logger.info("no se encontro el producto");
        } else {
            productoRepository.deleteById(id);
        }
    }
}
