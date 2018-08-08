package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.AgremiacaoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/agremiacoes")
public class AgremiacaoController {

    private AgremiacaoService agremiacaoService;

    @Autowired
    public AgremiacaoController(AgremiacaoService agremiacaoService) {
        this.agremiacaoService = agremiacaoService;
    }

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Page<Agremiacao>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<Agremiacao>> response = new Response<>();
        Page<Agremiacao> agremiacoes = this.agremiacaoService.findAll(page, count);
        response.setData(agremiacoes);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Agremiacao>> findById(@PathVariable("codigo") Long codigo) {
        Response<Agremiacao> response = new Response<>();
        Agremiacao agremiacao = this.agremiacaoService.findByCodigo(codigo);
        if (null == agremiacao) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(agremiacao);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Agremiacao>> cadastraAgremiacao(@RequestBody Agremiacao agremiacao) {
        Response<Agremiacao> response = new Response<>();
        final Agremiacao agremiacaoSaved = this.agremiacaoService.createOrUpdate(agremiacao);
        response.setData(agremiacaoSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Agremiacao>> atualizaAgremiacao(@RequestBody Agremiacao agremiacao) {
        Response<Agremiacao> response = new Response<>();
        final Agremiacao agremiacaoSaved = this.agremiacaoService.createOrUpdate(agremiacao);
        response.setData(agremiacaoSaved);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{codigoCampeonato}/inscritas")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<List<Agremiacao>> getAgremiacoesInscritas(
            @PathVariable(value = "codigoCampeonato") Long codigoCampeonato) {
        final List<Agremiacao> agremiacoes = this.agremiacaoService.getAgremiacoesInscritas(codigoCampeonato);
        HttpStatus status = (null == agremiacoes) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(agremiacoes,
                status);
    }

    @GetMapping(path = "/{codigo}/{categoria}/disponiveis")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<List<Agremiacao>>> getAgremiacoesDisponiveis(
            @PathVariable(value = "codigo") Long codigoCampeonato,
            @PathVariable(value = "categoria") String categoria) {
        Response<List<Agremiacao>> response = new Response<>();

        final List<Agremiacao> agremiacoes = this.agremiacaoService.getAgremiacoesDisponiveis(codigoCampeonato,
                categoria);
        response.setData(agremiacoes);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<?> deletaAgremiacao(@PathVariable(value = "codigo") Long codigo) {
        this.agremiacaoService.delete(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
