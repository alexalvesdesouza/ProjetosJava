package com.rmlocacoes.rmlocacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Endereco;
import com.rmlocacoes.rmlocacoes.repository.EnderecoRepository;

@Service
public class EnderecoService {

  @Autowired
  private EnderecoRepository repository;

  public Endereco salvaEndereco(final Endereco endereco) {
    return this.repository.save(endereco);
  }

}
