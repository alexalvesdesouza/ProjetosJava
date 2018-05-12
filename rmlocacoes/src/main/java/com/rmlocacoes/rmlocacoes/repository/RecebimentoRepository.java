package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.repository.CrudRepository;

import com.rmlocacoes.rmlocacoes.model.Receber;

public interface RecebimentoRepository extends CrudRepository<Receber, Long> {

  Receber findByCodigo(Long codigo);

}
