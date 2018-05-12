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

import com.rmlocacoes.rmlocacoes.model.Receber;
import com.rmlocacoes.rmlocacoes.service.ReceberService;

@Controller
@RequestMapping("/contas-receber")
public class ReceberController {

  private ReceberService service;

  @Autowired
  public ReceberController(ReceberService service) {
    super();
    this.service = service;
  }

  @RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
  public String eventoFormList() {
    return "evento/formEvento";
  }

  @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
  public String eventoForm(@RequestBody Receber receber) {
    //service.save(receber);
    return "redirect:/eventos";
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public @ResponseBody List<Receber> receberList() {
    return this.service.getRecebimentos();
  }
  
  @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
  public ResponseEntity<Receber> contasPagarMarcarComoPago(@PathVariable(value = "codigo") Long codigo) {
    Receber entitySaved = service.efetivaRecebimento(codigo);
    HttpStatus status = (null == entitySaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Receber>(entitySaved,
                                     status);
  }

}
