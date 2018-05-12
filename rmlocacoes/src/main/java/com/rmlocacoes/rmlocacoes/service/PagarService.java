package com.rmlocacoes.rmlocacoes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.FolhaPagamento;
import com.rmlocacoes.rmlocacoes.model.Pagar;
import com.rmlocacoes.rmlocacoes.repository.PagarRepository;
import com.rmlocacoes.rmlocacoes.util.BussinessForbbidenException;
import com.rmlocacoes.rmlocacoes.util.OrigemReceitasDespezasConstante;
import com.rmlocacoes.rmlocacoes.util.StatusEntidade;

@Service
public class PagarService {

  private PagarRepository       pagarRepository;
  private ContaVinculadaService contaVinculadaService;

  @Autowired
  public PagarService(PagarRepository pagarRepository, ContaVinculadaService contaVinculadaService) {
    super();
    this.pagarRepository = pagarRepository;
    this.contaVinculadaService = contaVinculadaService;
  }

  public Pagar cadastraContaPagar(Pagar entity) {
    entity.setStatus(StatusEntidade.A_PAGAR.name());
    entity.setDataCadastro(LocalDate.now());
    this.formattValor(entity);
    return this.pagarRepository.saveAndFlush(entity);
  }

  private void formattValor(Pagar entity) {
    BigDecimal valor = entity.getValor()
                             .setScale(2, RoundingMode.HALF_EVEN);
    entity.setValor(valor);
  }

  public List<Pagar> getContasAPagar() {
    return this.pagarRepository.findAll();
  }

  public Pagar atualizarContasPagar(Pagar entity) {
    this.formattValor(entity);
    return this.pagarRepository.saveAndFlush(entity);
  }

  public Pagar pagarConta(Long codigo) {

    Pagar contasPagar = this.buscaConta(codigo);
    this.validaStatus(contasPagar);
    contasPagar.setStatus(StatusEntidade.PAGO.name());
    contasPagar.setDataPagamento(LocalDate.now());
    this.contaVinculadaService.atualizaSaldo(contasPagar.getValor(), false);
    // entity.setUsuario(usuario);
    return this.pagarRepository.saveAndFlush(contasPagar);
  }

  private void validaStatus(Pagar conta) {
    if (conta.getStatus()
             .equals(StatusEntidade.PAGO.name()))
      throw new BussinessForbbidenException();
  }


  private Pagar buscaConta(Long codigo) {
    Pagar entity = this.pagarRepository.findByCodigo(codigo);
    return entity;
  }

  public void deletarContaPagar(Long codigo) {
    Pagar contasPagar = this.buscaConta(codigo);
    if (contasPagar.getStatus()
                   .equals(StatusEntidade.PAGO.name()))
      throw new BussinessForbbidenException();
    this.pagarRepository.delete(contasPagar);
  }

  public void geraContaFolhaPagamento(FolhaPagamento folha) {

    Pagar pagar = new Pagar();
    pagar.setDataCadastro(LocalDate.now());
    pagar.setDataVencimento(LocalDate.now());
    pagar.setStatus(StatusEntidade.A_PAGAR.name());
    pagar.setTipoDespesa(OrigemReceitasDespezasConstante.FOLHA_PAGAMENTO.name());

    pagar.setNumeroDocumento(folha.getCodigo()
                                  .toString());
    pagar.setObservacao(folha.getObservacao());
    pagar.setDescricao(folha.getObservacao());
    pagar.setValor(folha.getValor());

    this.formattValor(pagar);
    Pagar saved = this.pagarRepository.saveAndFlush(pagar);
    this.pagarConta(saved.getCodigo());

  }

  public void deletaContaPagarVinculadoFolhaPagamento(FolhaPagamento folhaPagamento) {
    Optional<Pagar> findFirst = this.getContasAPagar()
                                    .stream()
                                    .filter(conta -> conta.getTipoDespesa()
                                                          .equals(OrigemReceitasDespezasConstante.FOLHA_PAGAMENTO.name())

                                        && conta.getNumeroDocumento()
                                                .equals(folhaPagamento.getCodigo()
                                                                      .toString()))
                                    .findFirst();
    if (findFirst.isPresent()) {
      Pagar pagar = findFirst.get();
      pagar.setStatus(StatusEntidade.A_DELETAR.name());
      this.deletarContaPagar(pagar.getCodigo());
    }
  }

}
