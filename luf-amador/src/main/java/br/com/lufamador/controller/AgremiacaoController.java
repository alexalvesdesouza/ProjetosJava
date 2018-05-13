package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Tjdu;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Agremiacao> cadastraAgremiacao(@RequestBody Agremiacao agremiacao) {
        final Agremiacao agremiacaoSaved = this.agremiacaoService.cadastraAgremiacao(agremiacao);
        HttpStatus status = (null == agremiacaoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<>(agremiacaoSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Agremiacao>> getAgremiacoes() {
        final List<Agremiacao> agremiacoes = this.agremiacaoService.getAgremiacoes();
        HttpStatus status = (null == agremiacoes) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(agremiacoes, status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Agremiacao> atualizaAgremiacao(@RequestBody Agremiacao agremiacao) {
        final Agremiacao agremiacaoSuspenso = this.agremiacaoService.atulizarAgremiacao(agremiacao);
        HttpStatus status = (null == agremiacaoSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(agremiacaoSuspenso, status);
    }

    @RequestMapping(path = "/{codigo}/", method = RequestMethod.DELETE)
    public ResponseEntity<Tjdu> deletaAgremiacao(@PathVariable(value = "codigo") Long codigo) {
        this.agremiacaoService.deletarAgremiacao(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
