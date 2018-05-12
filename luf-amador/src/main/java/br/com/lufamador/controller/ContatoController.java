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

import br.com.lufamador.model.Contato;
import br.com.lufamador.service.ContatoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/contatos")
public class ContatoController {

    private ContatoService contatoService;

    @Autowired
    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Contato> cadastraContato(@RequestBody Contato contato) {
        final Contato contatoSaved = this.contatoService.cadastraContato(contato);
        HttpStatus status = (null == contatoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<Contato>(contatoSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Contato>> getContatos() {
        final List<Contato> contatos = this.contatoService.getContatos();
        HttpStatus status = (null == contatos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(contatos, status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Contato> atualizaContato(@RequestBody Contato contato) {
        final Contato contatoSuspenso = this.contatoService.atulizarContato(contato);
        HttpStatus status = (null == contatoSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<Contato>(contatoSuspenso, status);
    }



}
