package br.com.lufamador.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private JogoService jogoService;

    @RequestMapping(path = "/tempo-real/atualizar", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Jogo> atualizarJogo(@RequestBody Jogo jogo) throws NoSuchAlgorithmException {
        final Jogo jogoSaved = this.jogoService.atualizarJogo(jogo);
        jogoSaved.setDataAtualizacao(null);
        jogoSaved.setDataCriacao(null);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<>(jogoSaved,
                status);
    }

    @RequestMapping(path = "/encerrados/editar", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Jogo> editarJogoEncerrado(@RequestBody Jogo jogo) throws NoSuchAlgorithmException {
        final Jogo jogoSaved = this.jogoService.editarJogoEncerrado(jogo);
        jogoSaved.setDataAtualizacao(null);
        jogoSaved.setDataCriacao(null);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<>(jogoSaved,
                status);
    }

    @RequestMapping(path = "/tempo-real/encerrar", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Jogo> encerrarJogo(@RequestBody Jogo jogo) throws NoSuchAlgorithmException {
        final Jogo jogoSaved = this.jogoService.encerrarJogo(jogo);
        HttpStatus status = (null == jogoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<>(jogoSaved,
                status);
    }

    @GetMapping(value = "/tempo-real/{categoria}/list")
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Response<List<Jogo>>> getJogosTempoRealList(
            @PathVariable(value = "categoria") String categoria) {

        Response<List<Jogo>> response = new Response<>();
        final List<Jogo> jogos = this.jogoService.jogosTempoRealPorCategoria(categoria);
        response.setData(jogos);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/encerrados/{categoria}/{chave}/{fase}")
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Response<List<Jogo>>> getJogosEditList(
            @PathVariable(value = "categoria") String categoria,
            @PathVariable(value = "chave") String chave,
            @PathVariable(value = "fase") String fase,
            @QueryParam("dataJogo") String dataJogo) {

        Response<List<Jogo>> response = new Response<>();
        final List<Jogo> jogos = this.jogoService.getJogosEditList(categoria, chave,fase, dataJogo);
        response.setData(jogos);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/{codigoAgremiacao}/{codigoCampeonato}/all")
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Response<List<Jogo>>> getJogosFromAgremiacao(
            @PathVariable(value = "codigoAgremiacao") Long codigoAgremiacao,
            @PathVariable(value = "codigoCampeonato") Long codigoCampeonato) {

        Response<List<Jogo>> response = new Response<>();
        final List<Jogo> jogos = this.jogoService.getAllJogosPorAgremiacao(codigoAgremiacao, codigoCampeonato);
        response.setData(jogos);
        return ResponseEntity.ok(response);

    }

    @GetMapping(path = "/datas-partidas")
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Response<List<String>>>getDatasPartidas() {
        Response<List<String>> response = new Response<>();
        List<String> datas = this.jogoService.getDatasPartidas();
        response.setData(datas);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<?> deletarJogo(@PathVariable(value = "codigo") Long codigo) {
        this.jogoService.delete(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
