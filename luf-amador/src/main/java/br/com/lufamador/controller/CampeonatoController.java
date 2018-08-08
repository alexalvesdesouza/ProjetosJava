package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Campeonato;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.CampeonatoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/campeonatos")
public class CampeonatoController {

    private CampeonatoServiceImpl campeonatoService;

    @Autowired
    public CampeonatoController(CampeonatoServiceImpl campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @GetMapping(value = "{page}/{count}")
    public ResponseEntity<Response<Page<Campeonato>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<Campeonato>> response = new Response<>();
        Page<Campeonato> entity = this.campeonatoService.findAll(page, count);
        response.setData(entity);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/inscricoes-abertas")
    public ResponseEntity<Response<List<Campeonato>>> findAllOpen() {
        Response<List<Campeonato>> response = new Response<>();
        List<Campeonato> campeonatos = this.campeonatoService.getCampeonatos();
        response.setData(campeonatos);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Campeonato>> cadastraCampeonato(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.createOrUpdate(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Campeonato>> atualiza(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.createOrUpdate(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/tabela-jogos")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Campeonato>> registraTabelaJogos(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.registraTabelaJotos(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{codigo}/find")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Campeonato>> getCampeonato(
            @PathVariable(value = "codigo") Long codigo) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.findByCodigo(codigo);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/agremiacoes-inscrever")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Campeonato>> inscricaoAgremiacaoCampeonato(@RequestBody Campeonato campeonato) {
        Response<Campeonato> response = new Response<>();
        final Campeonato entity = this.campeonatoService.inscricaoAgremiacaoCampeonato(campeonato);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }


}
