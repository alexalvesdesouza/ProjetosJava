package com.rmlocacoes.rmlocacoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Funcionario;
import com.rmlocacoes.rmlocacoes.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

  private FuncionarioRepository funcionarioRepository;

  @Autowired
  public FuncionarioService(FuncionarioRepository funcionarioRepository) {
    this.funcionarioRepository = funcionarioRepository;
  }

  public Funcionario buscaFuncionario(final Long codigo) {
    return this.funcionarioRepository.findByCodigo(codigo);
  }

  public Funcionario salvaFuncionario(final Funcionario funcionario) {
    return funcionarioRepository.save(funcionario);
  }

  public List<Funcionario> getFuncionarios() {
   return this.funcionarioRepository.findAll();
  }

}
