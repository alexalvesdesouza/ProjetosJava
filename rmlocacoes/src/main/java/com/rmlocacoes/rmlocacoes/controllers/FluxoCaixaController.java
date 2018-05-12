package com.rmlocacoes.rmlocacoes.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmlocacoes.rmlocacoes.model.ContasAReceberDTO;
import com.rmlocacoes.rmlocacoes.model.ContaVinculada;
import com.rmlocacoes.rmlocacoes.service.ContaVinculadaService;
import com.rmlocacoes.rmlocacoes.service.FluxoCaixaService;

@Controller
public class FluxoCaixaController {

  private FluxoCaixaService      fluxoCaixaService;
  private ContaVinculadaService contasVinculadasService;

  @Autowired
  public FluxoCaixaController(FluxoCaixaService fluxoCaixaService, ContaVinculadaService contasVinculadasService) {
    this.fluxoCaixaService = fluxoCaixaService;
    this.contasVinculadasService = contasVinculadasService;
  }

  @RequestMapping(value = "/fluxo-caixa/contas-a-receber/{codigo}/receber", method = RequestMethod.PUT)
  public String marcarPedidoComoPago(@PathVariable("codigo") Long codigo) {
    fluxoCaixaService.marcarPedidoComoPago(codigo);
    return "fluxo/fluxoCaixa";
  }
 
  @RequestMapping(value = "/fluxo-caixa", method = RequestMethod.GET)
  public String fluxoCiaxa() {
    return "fluxo/fluxoCaixa";
  }

  @RequestMapping(value = "/fluxo-caixa/contas-a-pagar", method = RequestMethod.GET)
  public String contasAPagar() {
    return "fluxo/fluxoCaixa";
  }

  @RequestMapping(value = "/fluxo-caixa/contas-consolidadas", method = RequestMethod.GET)
  public @ResponseBody Map<String, Map<String, Object>> consolidados() {
    return fluxoCaixaService.loadConsolidados();
  }

  @RequestMapping(value = "/fluxo-caixa/contas-a-receber/{page}", method = RequestMethod.GET)
  public @ResponseBody Collection<ContasAReceberDTO> contasAReceber(@PathVariable("page") Long page) {
    return fluxoCaixaService.loadPedidos(page);
  }

  @RequestMapping(value = "/fluxo-caixa/contas-vinculadas/load", method = RequestMethod.GET)
  public @ResponseBody List<ContaVinculada> contasVinculadas() {
    return contasVinculadasService.getContasVinculadas();
  }

}
