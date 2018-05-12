package com.rmlocacoes.rmlocacoes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.FolhaPagamento;
import com.rmlocacoes.rmlocacoes.repository.FolhaPagamentoRepository;
import com.rmlocacoes.rmlocacoes.util.StatusEntidade;

@Service
public class FolhaPagamentoService {

  private FolhaPagamentoRepository pagarRepository;
  private PagarService             pagarService;
  private ContaVinculadaService    contaVinculadaService;

  @Autowired
  public FolhaPagamentoService(FolhaPagamentoRepository pagarRepository, PagarService pagarService, ContaVinculadaService contaVinculadaService) {
    super();
    this.pagarRepository = pagarRepository;
    this.pagarService = pagarService;
    this.contaVinculadaService = contaVinculadaService;
  }

  @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
  public FolhaPagamento cadastraContaFolhaPagamento(FolhaPagamento entity) {
    entity.setStatus(StatusEntidade.PAGO.name());
    entity.setDataCadastro(LocalDate.now());
    this.formattValor(entity);
    FolhaPagamento saveAndFlush = this.pagarRepository.saveAndFlush(entity);
    this.pagarService.geraContaFolhaPagamento(saveAndFlush);
    return saveAndFlush;
  }

  private void formattValor(FolhaPagamento entity) {
    BigDecimal valor = entity.getValor()
                             .setScale(2, RoundingMode.HALF_EVEN);
    entity.setValor(valor);
  }

  public List<FolhaPagamento> getContasAFolhaPagamento() {
    return this.pagarRepository.findAll();
  }

  public FolhaPagamento atualizarContasFolhaPagamento(FolhaPagamento entity) {
    FolhaPagamento folhaAntiga = this.buscaFolhaPagamento(entity.getCodigo());
    this.formattValor(entity);
    this.ajustaSaldoContaPrincipalPorAjusteFolhaPagamento(entity, folhaAntiga);
    FolhaPagamento saveAndFlush = this.pagarRepository.saveAndFlush(entity);
    return saveAndFlush;

  }

  private void ajustaSaldoContaPrincipalPorAjusteFolhaPagamento(FolhaPagamento folhaNova, FolhaPagamento folhaAntiga) {

    BigDecimal valorNovo = folhaNova.getValor();
    BigDecimal valorAntigo = folhaAntiga.getValor();
    Boolean incrementaSaldo = true;
    BigDecimal diferenca = new BigDecimal(BigDecimal.ZERO.toString());

    if (valorNovo.compareTo(valorAntigo) > 0) {
      diferenca = valorNovo.subtract(valorAntigo);
      incrementaSaldo = false;
    }
    if (valorNovo.compareTo(valorAntigo) < 0) {
      diferenca = valorAntigo.subtract(valorNovo);
    }
    this.contaVinculadaService.atualizaSaldo(diferenca, incrementaSaldo);
  }

  private FolhaPagamento buscaFolhaPagamento(Long codigo) {
    FolhaPagamento entity = this.pagarRepository.findByCodigo(codigo);
    return entity;
  }

  @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
  public ResponseEntity<?> deletarContaFolhaPagamento(Long codigo) {
    FolhaPagamento folhaPagamento = this.buscaFolhaPagamento(codigo);
    this.pagarRepository.delete(folhaPagamento);
    this.pagarService.deletaContaPagarVinculadoFolhaPagamento(folhaPagamento);
    this.contaVinculadaService.atualizaSaldo(folhaPagamento.getValor(), Boolean.TRUE);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
