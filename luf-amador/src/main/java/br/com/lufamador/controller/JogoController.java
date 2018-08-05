package br.com.lufamador.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.JogoService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/jogos")
public class JogoController {

    private JogoService jogoService;

    @Autowired
    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    @RequestMapping(path = "/tempo-real/atualizar", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Jogo> atualizarJogo(@RequestBody Jogo jogo) throws NoSuchAlgorithmException {
        final Jogo jogoSaved = this.jogoService.atualizarJogo(jogo);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<Jogo>(jogoSaved,
                status);
    }

    @RequestMapping(path = "/tempo-real/encerrar", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ADM_JOGOS')")
    public ResponseEntity<Jogo> encerrarJogo(@RequestBody Jogo jogo) throws NoSuchAlgorithmException {
        final Jogo jogoSaved = this.jogoService.encerrarJogo(jogo);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<Jogo>(jogoSaved,
                status);
    }

    @GetMapping(value = "/tempo-real/list")
    @PreAuthorize("hasAnyRole('ADM_JOGOS')")
    public ResponseEntity<Response<List<Jogo>>> getJogosTempoRealList() {

        Response<List<Jogo>> response = new Response<>();
        final List<Jogo> jogos = this.jogoService.getJogosTempoReal();
        response.setData(jogos);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/tempo-real")
    public ResponseEntity<List<Jogo>> getJogosTempoReal() {
        List<Jogo> jogos = this.jogoService.getJogosTempoReal();
        return ResponseEntity.ok(jogos);
    }

    @GetMapping(path = "/resultados")
    public ResponseEntity<List<Jogo>> getResultadoJogos() {
        List<Jogo> jogos = this.jogoService.getResultadosJogos();
        return ResponseEntity.ok(jogos);
    }

}
