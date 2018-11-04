package com.algaworks.algamoneyapi.resource;

import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    public CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listar() {
        return this.categoriaService.listaTodasCategorias();
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody @Valid Categoria categoria, HttpServletResponse response) {

        final Categoria categoriaSalva = this.categoriaService.cadastraCategoria(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(categoriaSalva.getCodigo()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> busca(@PathVariable Long codigo) {
        Categoria categoria = this.categoriaService.buscaCategoria(codigo);

        if (null == categoria)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(categoria);
    }
}
