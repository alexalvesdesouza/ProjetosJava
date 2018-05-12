package com.rmlocacoes.rmlocacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.DadosBancarios;
import com.rmlocacoes.rmlocacoes.repository.DadosBancariosRepository;

@Service
public class DadosBancariosService {

  @Autowired
  private DadosBancariosRepository repository;

  public DadosBancarios salvaDadosBancarios(final DadosBancarios entity) {
    return this.repository.save(entity);
  }

}
