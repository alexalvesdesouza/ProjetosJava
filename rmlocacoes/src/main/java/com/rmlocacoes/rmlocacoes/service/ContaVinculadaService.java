package com.rmlocacoes.rmlocacoes.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.ContaVinculada;
import com.rmlocacoes.rmlocacoes.repository.ContaVinculadaRepository;

@Service
public class ContaVinculadaService {

  private ContaVinculadaRepository repository;

  @Autowired
  public ContaVinculadaService(ContaVinculadaRepository repository) {
    super();
    this.repository = repository;
  }

  public List<ContaVinculada> getContasVinculadas() {
    return this.repository.findAll();
  }

  public void atualizaSaldo(BigDecimal valor, Boolean deposita) {
    Optional<ContaVinculada> findFirst = this.repository.findAll()
                                                        .stream()
                                                        .filter(conta -> conta.getContaPrincipal())
                                                        .findFirst();

    ContaVinculada conta = findFirst.get();
    BigDecimal saldoAtual = conta.getSaldo();
    if (deposita) {
      saldoAtual = saldoAtual.add(valor);
    } else {
      saldoAtual = saldoAtual.subtract(valor);
    }
   
    conta.setSaldo(saldoAtual);
    repository.saveAndFlush(conta);
  }

  public ContaVinculada cadastrarConta(ContaVinculada conta) {
    return this.repository.saveAndFlush(conta);
  }

  public ContaVinculada atualizarConta(ContaVinculada conta) {
    if (conta.getContaPrincipal())
      this.atualizaContasComoNaoPrincipal();

    return this.repository.saveAndFlush(conta);
  }

  private void atualizaContasComoNaoPrincipal() {
    this.getContasVinculadas()
        .forEach(conta -> {
          conta.setContaPrincipal(Boolean.FALSE);
          this.repository.saveAndFlush(conta);
        });
  }

  public ContaVinculada transferenciaEntreContas(ContaVinculada contaOrigem, ContaVinculada contaDestino) {
    this.atualizarConta(contaOrigem);
    this.atualizarConta(contaDestino);
    return contaDestino;
  }


}
