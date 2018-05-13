package br.com.lufamador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String indexCtrl() {
        return "tempoReal/tempo-real";
    }

    @RequestMapping(value = "/view-escala", method = RequestMethod.GET)
    public String fluxoCiaxa() {
        return "departamentoArbitros/escalas";
    }

    @RequestMapping(value = "/view-tempo-real", method = RequestMethod.GET)
    public String tempoReal() {
        return "tempoReal/tempo-real";
    }

    @RequestMapping(value = "/view-tjdu", method = RequestMethod.GET)
    public String getTjdu() {
        return "tjdu/form";
    }

    @RequestMapping(value = "/view-dpto-tecnico", method = RequestMethod.GET)
    public String dptoTecnico() {
        return "departamentoTecnico/form";
    }

    @RequestMapping(value = "/view-atletas", method = RequestMethod.GET)
    public String atletas() {
        return "atletas/form";
    }

    @RequestMapping(value = "/view-agremiacoes", method = RequestMethod.GET)
    public String agremiacoes() {
        return "agremiacoes/form";
    }

    @RequestMapping(value = "/view-tabela-jogos", method = RequestMethod.GET)
    public String tabelaJogos() {
        return "tabelaJogos/form";
    }
}
