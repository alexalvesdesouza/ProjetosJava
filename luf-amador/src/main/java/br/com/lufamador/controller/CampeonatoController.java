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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Campeonato;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.CampeonatoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/campeonatos")
public class CampeonatoController {

    @Autowired
    private CampeonatoServiceImpl campeonatoService;

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<Campeonato>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<Campeonato>> response = new Response<>();
        Page<Campeonato> entity = this.campeonatoService.findAll(page, count);
        response.setData(entity);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/inscricoes-abertas")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<List<Campeonato>>> findAllOpen() {
        Response<List<Campeonato>> response = new Response<>();
        List<Campeonato> campeonatos = this.campeonatoService.getCampeonatos();
        response.setData(campeonatos);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/andamento")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<List<Campeonato>>> findAllAndamento() {
        Response<List<Campeonato>> response = new Response<>();
        List<Campeonato> campeonatos = this.campeonatoService.getCampeonatosAndamento();
        response.setData(campeonatos);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Campeonato>> cadastraCampeonato(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.createOrUpdate(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Campeonato>> atualiza(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.createOrUpdate(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/tabela-jogos")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Campeonato>> registraTabelaJogos(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        Campeonato entity = this.campeonatoService.registraTabelaJotos(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{codigo}/find")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Campeonato>> getCampeonato(
            @PathVariable(value = "codigo") Long codigo) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.findByCodigo(codigo);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/agremiacoes-inscrever")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<Campeonato>> inscricaoAgremiacaoCampeonato(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        Campeonato entity = this.campeonatoService.inscricaoAgremiacaoCampeonato(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public void delete(@PathVariable(value = "codigo") Long codigo) {
        this.campeonatoService.delete(codigo);
    }


}
