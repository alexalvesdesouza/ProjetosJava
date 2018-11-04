package com.algaworks.algamoneyapi.resource;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    public PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listar() {
        return this.pessoaService.listaTodasPessoas();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response) {

        final Pessoa pessoaSalva = this.pessoaService.cadastraPessoa(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(pessoaSalva.getCodigo()).toUri();

        response.setHeader("Location", uri.toASCIIString());

        return ResponseEntity.created(uri).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> busca(@PathVariable Long codigo) {
        Pessoa pessoa = this.pessoaService.buscaPessoa(codigo);

        if (null == pessoa)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(pessoa);
    }
}
