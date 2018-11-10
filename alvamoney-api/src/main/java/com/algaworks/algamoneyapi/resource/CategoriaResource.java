package com.algaworks.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    public CategoriaService categoriaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    @GetMapping
    public List<Categoria> listar() {
        return this.categoriaService.listaTodasCategorias();
    }

    @PreAuthorize(value = "hasAuthority('ROLE_CRIAR_CATEGORIA') and #oauth2.hasScope('read')")
    @PostMapping
    public ResponseEntity<Categoria> criar(@RequestBody @Valid Categoria categoria, HttpServletResponse response) {

        final Categoria categoriaSalva = this.categoriaService.cadastraCategoria(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> busca(@PathVariable Long codigo) {
        Categoria categoria = this.categoriaService.buscaCategoria(codigo);

        if (null == categoria)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(categoria);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_DELETAR_CATEGORIA') and #oauth2.hasScope('read')")
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleta(@PathVariable Long codigo) {
        this.categoriaService.deletaCategoria(codigo);
    }
}
