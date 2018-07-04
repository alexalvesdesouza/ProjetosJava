package br.com.lufamador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.service.impl.ConfrontoService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/confrontos")
public class ConfrontoController {

    private ConfrontoService confrontoService;

    @Autowired
    public ConfrontoController(ConfrontoService confrontoService) {
        this.confrontoService = confrontoService;
    }

    @RequestMapping(path = "/confronto-cadastrar/", method = RequestMethod.POST)
    public ResponseEntity<Jogo> cadastraConfronto(@RequestBody Jogo jogo) {
        final Jogo jogoSaved = this.confrontoService.cadastraConfronto(jogo);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<Jogo>(jogoSaved,
                status);
    }


}
