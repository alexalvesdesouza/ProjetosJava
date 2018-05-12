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

import com.rmlocacoes.rmlocacoes.model.Pagar;
import com.rmlocacoes.rmlocacoes.service.PagarService;

@Controller
public class PagarController {

  private PagarService service;

  @Autowired
  public PagarController(PagarService service) {
    super();
    this.service = service;
  }

  @RequestMapping(value = "/contas-pagar/list", method = RequestMethod.GET)
  public @ResponseBody List<Pagar> contasAPagarList() {
    return this.service.getContasAPagar();
  }

  @RequestMapping(value = "/contas-pagar/cadastrar", method = RequestMethod.POST)
  public ResponseEntity<Pagar> contasPagarCadastrar(@RequestBody Pagar entity) {
    Pagar entitySaved = service.cadastraContaPagar(entity);
    HttpStatus status = (null == entitySaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Pagar>(entitySaved,
                                     status);
  }

  @RequestMapping(value = "/contas-pagar/atualizar", method = RequestMethod.PUT)
  public ResponseEntity<Pagar> contasPagarAtualizar(@RequestBody Pagar entity) {
    Pagar entitySaved = service.atualizarContasPagar(entity);
    HttpStatus status = (null == entitySaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Pagar>(entitySaved,
                                     status);
  }

  @RequestMapping(value = "/contas-pagar/{codigo}/deletar", method = RequestMethod.DELETE)
  public ResponseEntity<?> contasPagarDeletar(@PathVariable(value = "codigo") Long codigo) {
    service.deletarContaPagar(codigo);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "/contas-pagar/{codigo}/pagar", method = RequestMethod.PUT)
  public ResponseEntity<Pagar> contasPagarMarcarComoPago(@PathVariable(value = "codigo") Long codigo) {
    Pagar entitySaved = service.pagarConta(codigo);
    HttpStatus status = (null == entitySaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Pagar>(entitySaved,
                                     status);
  }

}
