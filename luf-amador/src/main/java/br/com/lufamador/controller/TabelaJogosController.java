package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.TabelaJogos;
import br.com.lufamador.service.TabelaJogosService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tabela-jogos")
public class TabelaJogosController {

    private TabelaJogosService tabelaJogosService;

    @Autowired
    public TabelaJogosController(TabelaJogosService tabelaJogosService) {
        this.tabelaJogosService = tabelaJogosService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TabelaJogos> cadastraTabelaJogos(@RequestBody TabelaJogos tabelaJogos) {
        final TabelaJogos tabelaJogosSaved = this.tabelaJogosService.cadastraTabelaJogos(tabelaJogos);
        HttpStatus status = (null == tabelaJogosSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<>(tabelaJogosSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TabelaJogos>> getTabelaJogos() {
        final List<TabelaJogos> tabelaJogos = this.tabelaJogosService.getTabelaJogoss();
        HttpStatus status = (null == tabelaJogos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(tabelaJogos, status);
    }

//    @RequestMapping(path = "/tempo-real" ,method = RequestMethod.GET)
//    public ResponseEntity<List<TabelaJogos>> getTabelaTempoReal() {
//        final List<TabelaJogos> tabelaJogoss = this.tabelaJogosService.getTabelaTempoReal();
//        HttpStatus status = (null == tabelaJogoss) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
//        return new ResponseEntity<>(tabelaJogoss, status);
//    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<TabelaJogos> atualizaTabelaJogos(@RequestBody TabelaJogos tabelaJogos) {
        final TabelaJogos tabelaJogosSuspenso = this.tabelaJogosService.atualizarTabelaJogos(tabelaJogos);
        HttpStatus status = (null == tabelaJogosSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(tabelaJogosSuspenso, status);
    }

}
