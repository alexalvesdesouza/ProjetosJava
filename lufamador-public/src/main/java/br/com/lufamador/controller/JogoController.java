package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.JogoService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/jogos")
public class JogoController {

    @Autowired
    private JogoService jogoService;

    @GetMapping(value = "/tempo-real/{categoria}")
    public ResponseEntity<Response<List<Jogo>>> getJogosTempoReal(@PathVariable(value = "categoria") String categoria) {
        Response<List<Jogo>> response = new Response<>();
        final List<Jogo> jogos = this.jogoService.jogosTempoRealPorCategoria(categoria);
        response.setData(jogos);
        response.setParam("newGol");
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/resultados/{categoria}/{dataRodada}")
    public ResponseEntity<List<Jogo>> getResultadoJogos(@PathVariable(value = "categoria") String categoria,
            @PathVariable(value = "dataRodada") String dataRodada) {
        List<Jogo> jogos = this.jogoService.getResultadosJogos(categoria, dataRodada);
        return ResponseEntity.ok(jogos);
    }

    @GetMapping(path = "/{categoria}/datas-partidas")
    public ResponseEntity<List<String>> getDatasPartidas(@PathVariable(value = "categoria") String categoria) {
        List<String> datas = this.jogoService.getDatasPartidas(categoria);
        return ResponseEntity.ok(datas);
    }

}
