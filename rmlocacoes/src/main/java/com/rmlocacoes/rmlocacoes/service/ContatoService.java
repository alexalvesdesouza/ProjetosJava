package com.rmlocacoes.rmlocacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Contato;
import com.rmlocacoes.rmlocacoes.repository.ContatoRepository;

@Service
public class ContatoService {

  @Autowired
  private ContatoRepository repository;

  public Contato salvaContato(final Contato contato) {
    return this.repository.save(contato);
  }

}
