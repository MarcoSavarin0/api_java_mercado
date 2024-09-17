package com.example.demo.controller;

import com.example.demo.Service.implement.ProductoService;
import com.example.demo.entity.Producto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("/productos")
public class ProductoController {
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/admin/guardar")
    public ResponseEntity<Producto> guardarProducto(@Valid @RequestBody Producto producto) {
        Producto productoGuardado = productoService.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listarTodosLosProductos() {
        List<Producto> listProductos = productoService.buscarTodosLosProductos();
        return ResponseEntity.status(HttpStatus.OK).body(listProductos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorIdProducto(@PathVariable Long id) {
        Optional<Producto> productoEncontrado = productoService.buscarPorIdProducto(id);
        return productoEncontrado.isPresent() ? ResponseEntity.ok(productoEncontrado.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("/admin/editar")
    public ResponseEntity<Producto> editarProducto(@Valid @RequestBody Producto producto) {
        productoService.editarProducto(producto);
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }

    @DeleteMapping("/admin/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.ok("ok");
    }
}
