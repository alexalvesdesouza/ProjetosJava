package br.com.lufamador.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.Agremiacao;

public interface AgremiacaoService {

  Page<Agremiacao> findAll(int page, int count);

  Agremiacao createOrUpdate(Agremiacao agremiacao);

  Agremiacao findByCodigo(Long id);

  void delete(Long id);
  
  List<Agremiacao> getAgremiacoesInscritas(final Long codigoCampeonato);
  
  List<Agremiacao> getAgremiacoesDisponiveis(final Long codigoCampeonato, final String categoria);

  Map<String, Agremiacao> findAllMap(int page, int count);

}
