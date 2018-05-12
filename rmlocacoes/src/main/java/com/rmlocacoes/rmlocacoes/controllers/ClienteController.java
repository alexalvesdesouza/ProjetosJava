  package com.rmlocacoes.rmlocacoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmlocacoes.rmlocacoes.model.Cliente;
import com.rmlocacoes.rmlocacoes.service.ClienteService;

@Controller
public class ClienteController {

  @Autowired
  private ClienteService clienteService;

  @RequestMapping(value = "/cadastrarCliente", method = RequestMethod.GET)
  public String eventoFormList() {
    return "cliente/formCliente";
  }

  @GetMapping(path = "/carregarClientes")
  public @ResponseBody Iterable<Cliente> carregarClientes() {
    return clienteService.getClientes();
  }

  @PostMapping(path = "/cadastrarCliente")
  public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {

    final Cliente clienteSaved = clienteService.salvaCliente(cliente);
    HttpStatus status = (null == clienteSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
    return new ResponseEntity<Cliente>(clienteSaved,
                                       status);
  }

  @PutMapping(path = "/editarCliente")
  public ResponseEntity<Cliente> editarCliente(@RequestBody Cliente cliente) {

    final Cliente clienteSaved = clienteService.salvaCliente(cliente);
    HttpStatus status = (null == clienteSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;

    return new ResponseEntity<Cliente>(clienteSaved,
                                       status);
  }

  @GetMapping(path = "/clientes")
  public String pedidosList() {
    return "cliente/clientesList";
  }

}
