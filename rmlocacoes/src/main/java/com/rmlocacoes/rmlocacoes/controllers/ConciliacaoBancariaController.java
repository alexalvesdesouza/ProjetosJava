package com.rmlocacoes.rmlocacoes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmlocacoes.rmlocacoes.model.ContaVinculada;
import com.rmlocacoes.rmlocacoes.service.ContaVinculadaService;

@Controller
@RequestMapping("/conciliacao-bancaria")
public class ConciliacaoBancariaController {

  private ContaVinculadaService contaVinculadaService;

  @Autowired
  public ConciliacaoBancariaController(ContaVinculadaService contaVinculadaService) {
    super();
    this.contaVinculadaService = contaVinculadaService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String marcarPedidoComoPago() {
    return "conciliacao/formConciliacao";
  }

  @GetMapping(path = "/contas-vinculadas/list")
  public @ResponseBody List<ContaVinculada> carregarContas() {
    return contaVinculadaService.getContasVinculadas();
  }

  @RequestMapping(value = "/contas-vinculadas/cadastrar", method = RequestMethod.POST)
  public ResponseEntity<ContaVinculada> contasVinculadas(@RequestBody ContaVinculada conta) {
    ContaVinculada save = this.contaVinculadaService.cadastrarConta(conta);
    HttpStatus status = save != null ? HttpStatus.CREATED : HttpStatus.CONFLICT;

    return new ResponseEntity<ContaVinculada>(save,
                                              status);
  }

  @RequestMapping(value = "/contas-vinculadas/atualizar", method = RequestMethod.PUT)
  public ResponseEntity<ContaVinculada> contasVinculadasAtualizar(@RequestBody ContaVinculada conta) {
    ContaVinculada save = this.contaVinculadaService.atualizarConta(conta);
    HttpStatus status = save != null ? HttpStatus.OK : HttpStatus.CONFLICT;

    return new ResponseEntity<ContaVinculada>(save,
                                              status);
  }

  @RequestMapping(value = "/contas-vinculadas/transferencia", method = RequestMethod.PUT)
  public ResponseEntity<ContaVinculada> contasVinculadasTransferencia(@RequestBody ContaVinculada contaOrigem,
                                                                      @RequestBody ContaVinculada contaDestino) {
    ContaVinculada save = this.contaVinculadaService.transferenciaEntreContas(contaOrigem, contaDestino);
    HttpStatus status = save != null ? HttpStatus.OK : HttpStatus.CONFLICT;

    return new ResponseEntity<ContaVinculada>(save,
                                              status);
  }

}
