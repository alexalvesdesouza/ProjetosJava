package com.rmlocacoes.rmlocacoes.controllers;

import java.util.List;

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

import com.rmlocacoes.rmlocacoes.model.Funcionario;
import com.rmlocacoes.rmlocacoes.service.FuncionarioService;

@Controller
public class FuncionarioController {

  private FuncionarioService funcionarioService;

  @Autowired
  public FuncionarioController(FuncionarioService funcionarioService) {
    super();
    this.funcionarioService = funcionarioService;
  }

  @RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.GET)
  public String eventoFormList() {
    return "funcionario/formFuncionario";
  }

  @GetMapping(path = "/carregarFuncionarios")
  public @ResponseBody List<Funcionario> carregarFuncionarios() {
    return funcionarioService.getFuncionarios();
  }

  @PostMapping(path = "/cadastrarFuncionario")
  public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario funcionario) {

    final Funcionario funcionarioSaved = funcionarioService.salvaFuncionario(funcionario);
    HttpStatus status = (null == funcionarioSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;

    return new ResponseEntity<Funcionario>(funcionarioSaved,
                                           status);
  }

  @PutMapping(path = "/editarFuncionario")
  public ResponseEntity<Funcionario> editarFuncionario(@RequestBody Funcionario funcionario) {

    final Funcionario funcionarioSaved = funcionarioService.salvaFuncionario(funcionario);
    HttpStatus status = (null == funcionarioSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;

    return new ResponseEntity<Funcionario>(funcionarioSaved,
                                           status);
  }

}
