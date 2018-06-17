package br.com.lufamador.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.service.JogoService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/jogos")
public class JogoController {

    private JogoService jogoService;

    @Autowired
    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Jogo> cadastraJogo(@RequestBody Jogo jogo) {
        final Jogo jogoSaved = this.jogoService.cadastraJogo(jogo);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<Jogo>(jogoSaved,
                status);
    }

    @RequestMapping(path = "/tempo-real/atualizar",  method = RequestMethod.PUT)
    public ResponseEntity<Jogo> atualizarJogo(@RequestBody Jogo jogo) throws NoSuchAlgorithmException {
        final Jogo jogoSaved = this.jogoService.atualizarJogo(jogo);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<Jogo>(jogoSaved,
                status);
    }
    
    @RequestMapping(path = "/tempo-real/encerrar",  method = RequestMethod.PUT)
    public ResponseEntity<Jogo> encerrarJogo(@RequestBody Jogo jogo) throws NoSuchAlgorithmException {
      final Jogo jogoSaved = this.jogoService.encerrarJogo(jogo);
      HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
      return new ResponseEntity<Jogo>(jogoSaved,
          status);
    }

    @RequestMapping(path = "/tempo-real",method = RequestMethod.GET)
    public ResponseEntity<List<Jogo>> getJogosTempoReal() {
        final List<Jogo> jogos = this.jogoService.getJogosTempoReal();
        HttpStatus status = (null == jogos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(jogos, status);
    }
    
    @RequestMapping(path = "/resultados",method = RequestMethod.GET)
    public ResponseEntity<List<Jogo>> getResultadoJogos() {
      final List<Jogo> jogos = this.jogoService.getResultadosJogos();
      HttpStatus status = (null == jogos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
      return new ResponseEntity<>(jogos, status);
    }

}
