package br.com.lufamador.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

  @RequestMapping(method = RequestMethod.GET, value = "/login")
  public String loginForm() {
    return "login";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/logout")
  public String logoutForm() {
    return "logout";
  }

  @RequestMapping(method = RequestMethod.GET, value = "/template")
  public String indexCtrl() {
    return "template";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-escala")
  public String fluxoCiaxa() {
    return "departamentoArbitros/escalas";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-tempo-real")
  public String tempoReal() {
    return "tempoReal/tempo-real";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-tjdu")
  public String getTjdu() {
    return "tjdu/form";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-dpto-tecnico")
  public String dptoTecnico() {
    return "departamentoTecnico/form";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-atletas")
  public String atletas() {
    return "atletas/form";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-campeonato")
  public String competicoes() {
    return "campeonato/form";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-agremiacoes")
  public String agremiacoes() {
    return "agremiacoes/form";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/view-tabela-jogos")
  public String tabelaJogos() {
    return "tabela/form";
  }
}
