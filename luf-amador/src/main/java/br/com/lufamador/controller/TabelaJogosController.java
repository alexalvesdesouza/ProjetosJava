package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.lufamador.model.TabelaJogos;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.TabelaJogosService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tabela-jogos")
public class TabelaJogosController {

    @Autowired
    private TabelaJogosService tabelaJogosService;

    @PostMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<TabelaJogos>> cadastraTabelaJogos(@RequestBody TabelaJogos tabelaJogos) {
        Response<TabelaJogos> response = new Response<>();
        final TabelaJogos entity = this.tabelaJogosService.cadastraTabelaJogos(tabelaJogos);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{codigo}/campeonato")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<List<TabelaJogos>>> getTabelaJogosPorCampeonato(
            @PathVariable(value = "codigo") Long codigo) {
        Response<List<TabelaJogos>> response = new Response<>();
        final List<TabelaJogos> tabelaJogos = this.tabelaJogosService.getTabelaJogosPorCampeonato(codigo);
        response.setData(tabelaJogos);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<TabelaJogos>> atualizaTabelaJogos(@RequestBody TabelaJogos tabelaJogos) {
        Response<TabelaJogos> response = new Response<>();
        final TabelaJogos entity = this.tabelaJogosService.atualizarTabelaJogos(tabelaJogos);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

}
