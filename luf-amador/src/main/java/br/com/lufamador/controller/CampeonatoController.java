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

import br.com.lufamador.model.Campeonato;
import br.com.lufamador.service.impl.CampeonatoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/campeonatos")
public class CampeonatoController {

    private CampeonatoService campeonatoService;

    @Autowired
    public CampeonatoController(CampeonatoService campeonatoService) {
        this.campeonatoService = campeonatoService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Campeonato> cadastraCampeonato(@RequestBody Campeonato campeonato) {
        final Campeonato campeonatoSaved = this.campeonatoService.cadastraCampeonato(campeonato);
        HttpStatus status = (null == campeonatoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<>(campeonatoSaved,
                status);
    }

    @RequestMapping(path = "/{inscricoesAbertas}/", method = RequestMethod.GET)
    public ResponseEntity<List<Campeonato>> getCampeonatos(
            @PathVariable(value = "inscricoesAbertas") Boolean inscricoesAbertas) {

        final List<Campeonato> campeonatos = this.campeonatoService.getCampeonatos(inscricoesAbertas);
        HttpStatus status = (null == campeonatos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(campeonatos, status);
    }

    @RequestMapping(path = "/{codigoCampeonato}/busca", method = RequestMethod.GET)
    public ResponseEntity<Campeonato> getCampeonato(
            @PathVariable(value = "codigoCampeonato") Long codigoCampeonato) {
        final Campeonato campeonato = this.campeonatoService.findCampeonato(codigoCampeonato);
        HttpStatus status = (null == campeonato) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(campeonato, status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Campeonato> atualizaCampeonato(@RequestBody Campeonato campeonato) {
        final Campeonato campeonatoSuspenso = this.campeonatoService.atualizarCampeonato(campeonato);
        HttpStatus status = (null == campeonatoSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(campeonatoSuspenso, status);
    }


    @RequestMapping(path = "/agremiacoes-inscrever", method = RequestMethod.PUT)
    public ResponseEntity<Campeonato> inscricaoAgremiacaoCampeonato(@RequestBody Campeonato campeonato) {
        final Campeonato campeonadoSaved = this.campeonatoService.inscricaoAgremiacaoCampeonato(campeonato);
        HttpStatus status = (null == campeonadoSaved) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<>(campeonadoSaved, status);
    }


}
