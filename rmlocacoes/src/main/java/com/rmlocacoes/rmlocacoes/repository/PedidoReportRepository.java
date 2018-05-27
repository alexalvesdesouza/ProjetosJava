package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rmlocacoes.rmlocacoes.model.PedidoReport;

public interface PedidoReportRepository extends JpaRepository<PedidoReport, String> {

  @Query(value = "select * from rm_pedido_report where chave_diaria = ?1", nativeQuery = true)
  PedidoReport buscaChaveDiaria(String chave);
}
