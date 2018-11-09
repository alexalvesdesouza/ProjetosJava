package com.algaworks.algamoneyapi.resource;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
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
    private PessoaService pessoaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Pessoa> listar() {
        return this.pessoaService.listaTodasPessoas();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response) {

        final Pessoa pessoaSalva = this.pessoaService.cadastraPessoa(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> busca(@PathVariable Long codigo) {
        Pessoa pessoa = this.pessoaService.buscaPessoa(codigo);

        if (null == pessoa)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(pessoa);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleta(@PathVariable Long codigo) {
        this.pessoaService.deletaPessoa(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> atualiza(@PathVariable Long codigo, @RequestBody @Valid Pessoa pessoa) {
        return this.pessoaService.atulizaPessoa(codigo, pessoa);

    }

    @PutMapping("/{codigo}/ativa")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativaInativa(@PathVariable Long codigo, @RequestBody Boolean ativa) {
         this.pessoaService.ativaInativaPessoa(codigo, ativa);
    }

}
