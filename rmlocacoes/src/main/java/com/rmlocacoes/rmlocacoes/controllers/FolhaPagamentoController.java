package com.rmlocacoes.rmlocacoes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmlocacoes.rmlocacoes.model.FolhaPagamento;
import com.rmlocacoes.rmlocacoes.service.FolhaPagamentoService;

@Controller
public class FolhaPagamentoController {

  private FolhaPagamentoService service;

  @Autowired
  public FolhaPagamentoController(FolhaPagamentoService service) {
    this.service = service;
  }

  @RequestMapping(value = "/folha-pagamento/list", method = RequestMethod.GET)
  public @ResponseBody List<FolhaPagamento> contasAFolhaPagamentoList() {
    return this.service.getContasAFolhaPagamento();
  }

  @RequestMapping(value = "/folha-pagamento/cadastrar", method = RequestMethod.POST)
  public ResponseEntity<FolhaPagamento> contasFolhaPagamentoCadastrar(@RequestBody FolhaPagamento entity) {
    FolhaPagamento entitySaved = service.cadastraContaFolhaPagamento(entity);
    HttpStatus status = (null == entitySaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<FolhaPagamento>(entitySaved,
                                              status);
  }

  @RequestMapping(value = "/folha-pagamento/atualizar", method = RequestMethod.PUT)
  public ResponseEntity<FolhaPagamento> contasFolhaPagamentoAtualizar(@RequestBody FolhaPagamento entity) {
    FolhaPagamento entitySaved = service.atualizarContasFolhaPagamento(entity);
    HttpStatus status = (null == entitySaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<FolhaPagamento>(entitySaved,
                                              status);
  }

  @RequestMapping(value = "/folha-pagamento/{codigo}/deletar", method = RequestMethod.DELETE)
  public ResponseEntity<?> contasFolhaPagamentoDeletar(@PathVariable(value = "codigo") Long codigo) {
    service.deletarContaFolhaPagamento(codigo);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
