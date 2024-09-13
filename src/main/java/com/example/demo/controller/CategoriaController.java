package com.example.demo.controller;

import com.example.demo.Service.implement.CategoriaService;
import com.example.demo.entity.Categoria;
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
@RequestMapping("/categoria")
public class CategoriaController {
    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<Categoria> guardarCategoria(@Valid @RequestBody Categoria categoria){
        Categoria categoriaAguardar = categoriaService.guardarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaAguardar);
    }
    @GetMapping
    public  ResponseEntity<List<Categoria>> listarTodasLasCat(){
        return ResponseEntity.ok(categoriaService.buscarTodosLasCategorias());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorIdCat(@PathVariable Long id){
        Optional<Categoria> categoriaMatch = categoriaService.buscarPorIdCategoria(id);
        return categoriaMatch.isPresent() ?  ResponseEntity.ok(categoriaMatch.get()) : ResponseEntity.notFound().build();
    }
    @PutMapping
    ResponseEntity<Categoria> editarCategoria(@Valid @RequestBody Categoria categoria){
        categoriaService.editarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("ok");
    }

}
