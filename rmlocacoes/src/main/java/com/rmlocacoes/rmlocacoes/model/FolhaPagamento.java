package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmlocacoes.rmlocacoes.util.LocalDateDeserializer;
import com.rmlocacoes.rmlocacoes.util.LocalDateSerializer;

@Entity
@Table(name = "rm_folha_pagamento")
public class FolhaPagamento implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;

  @OneToOne
  private Funcionario       funcionario;
  private BigDecimal        valor;
  private String            tipoFolhaPagamento;
  private String            observacao;
  private String            status;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate         dataPagamento;
  private LocalDate         dataCadastro;

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public Funcionario getFuncionario() {
    return funcionario;
  }

  public void setFuncionario(Funcionario funcionario) {
    this.funcionario = funcionario;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getTipoFolhaPagamento() {
    return tipoFolhaPagamento;
  }

  public void setTipoFolhaPagamento(String tipoFolhaPagamento) {
    this.tipoFolhaPagamento = tipoFolhaPagamento;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDate getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(LocalDate dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public LocalDate getDataCadastro() {
    return dataCadastro;
  }

  public void setDataCadastro(LocalDate dataCadastro) {
    this.dataCadastro = dataCadastro;
  }

}
