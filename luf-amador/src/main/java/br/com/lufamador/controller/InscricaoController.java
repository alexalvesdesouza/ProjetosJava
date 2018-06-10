package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Inscricao;
import br.com.lufamador.service.InscricaoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/inscricoes")
public class InscricaoController {

    private InscricaoService inscricaoService;

    @Autowired
    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Inscricao> cadastraInscricao(@RequestBody Inscricao inscricao) {
//        final Inscricao inscricaoSaved = this.inscricaoService.cadastraInscricao(inscricao);
//        HttpStatus status = (null == inscricaoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
//        return new ResponseEntity<>(inscricaoSaved,
//                status);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Inscricao>> getInscricoes() {
        final List<Inscricao> inscricoes = this.inscricaoService.getInscricoes();
        HttpStatus status = (null == inscricoes) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(inscricoes, status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Inscricao> atualizaInscricao(@RequestBody Inscricao inscricao) {
        final Inscricao inscricaoSuspenso = this.inscricaoService.atualizarInscricao(inscricao);
        HttpStatus status = (null == inscricaoSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(inscricaoSuspenso, status);
    }

    @RequestMapping(path = "/{codigo}/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletaInscricao(@PathVariable("codigo") Long codigo) {
        this.inscricaoService.excluirInscricao(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

    @RequestMapping(path = "/{codigoAgremiacao}/inscrever", method = RequestMethod.POST)
    public ResponseEntity<Inscricao> inscricaoAgremiacaoCampeonato(@PathVariable(value = "codigoAgremiacao") Long codigoAgremiacao) {
        final Inscricao inscricao = this.inscricaoService.inscricaoAgremiacaoCampeonato(codigoAgremiacao);
        HttpStatus status = (null == inscricao) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(inscricao, status);
    }

}
