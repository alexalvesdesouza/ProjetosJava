package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.Atleta;

public interface AtletaService {
  
  Page<Atleta> findAll(int page, int count);
  
  Atleta createOrUpdate(Atleta atleta);

  Atleta findByCodigo(Long id);

  void delete(Long id);

}
