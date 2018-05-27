package com.rmlocacoes.rmlocacoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.PedidoReport;
import com.rmlocacoes.rmlocacoes.repository.PedidoReportRepository;

@Service
public class PedidoReportService {

  private PedidoReportRepository pedidoReportRepository;

  @Autowired
  public PedidoReportService(PedidoReportRepository pedidoReportRepository) {
    this.pedidoReportRepository = pedidoReportRepository;
  }

  private PedidoReport buscaPedidoReport(final String chaveDiaria) {
    return pedidoReportRepository.buscaChaveDiaria(chaveDiaria);
  }

  public Boolean isChaveExistente(final String chaveDiaria) {
    PedidoReport buscaPedidoReport = this.buscaPedidoReport(chaveDiaria);
    return buscaPedidoReport != null;
  }

  public void insereChaveDiaria(String chave) {
    this.pedidoReportRepository.save(new PedidoReport(chave));
  }

}
