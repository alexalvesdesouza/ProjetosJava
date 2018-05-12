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

import br.com.lufamador.model.Atleta;
import br.com.lufamador.service.AtletaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/atletas")
public class AtletaController {

    private AtletaService atletaService;

    @Autowired
    public AtletaController(AtletaService atletaService) {
        this.atletaService = atletaService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Atleta> cadastraAtleta(@RequestBody Atleta atleta) {
        final Atleta atletaSaved = this.atletaService.cadastraAtleta(atleta);
        HttpStatus status = (null == atletaSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<Atleta>(atletaSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Atleta>> getAtletas() {
        final List<Atleta> atletas = this.atletaService.getAtletas();
        HttpStatus status = (null == atletas) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(atletas, status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Atleta> atualizaAtleta(@RequestBody Atleta atleta) {
        final Atleta atletaSuspenso = this.atletaService.atulizarAtleta(atleta);
        HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<Atleta>(atletaSuspenso, status);
    }

    @RequestMapping(path = "/{codigo}/baixa-suspensao/", method = RequestMethod.PUT)
    public ResponseEntity<Atleta> baixaSuspensaoAtleta(@PathVariable("codigo") Long codigo) {
        final Atleta atletaSuspenso = this.atletaService.baixarSuspensaoAtleta(codigo);
        HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<Atleta>(atletaSuspenso, status);
    }

    @RequestMapping(path = "/{codigo}/suspender/", method = RequestMethod.PUT)
    public ResponseEntity<Atleta> suspendeAtleta(@PathVariable("codigo") Long codigo) {
        final Atleta atletaSuspenso = this.atletaService.suspenderAtleta(codigo);
        HttpStatus status = (null == atletaSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<Atleta>(atletaSuspenso, status);
    }

    @RequestMapping(path = "/{codigo}/", method = RequestMethod.DELETE)
    public ResponseEntity deletaAtleta(@PathVariable("codigo") Long codigo) {
        this.atletaService.excluirAtleta(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

}
