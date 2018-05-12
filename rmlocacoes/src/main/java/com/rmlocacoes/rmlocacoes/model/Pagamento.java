package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rm_pagamento")
public class Pagamento implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID   = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;

  private String            formaPagamento;
  private String            prazoPagamento;
  private String            status;
  private String            motivoCancelamento;
  private BigDecimal        vlrTotalDivida;
  private BigDecimal        vlrParcela;
  private BigDecimal        vlrPago;
  private BigDecimal        vlrTxEntrega;
  private BigDecimal        vlrMulta;
  private BigDecimal        vlrMultaExtravio;
  private BigDecimal        vlrMultaAvaria;
  private BigDecimal        vlrTotalMultas;
  private LocalDateTime     dataCriacao;
  private LocalDateTime     dataVencimento;
  private LocalDateTime     dataPagamento;
  private LocalDateTime     dataCancelamento;
  private Boolean           pagamentoVencido   = false;
  private Boolean           pagamentoCancelado = false;
  private Boolean           efetuouPagamento   = false;
  private Long              totalParcelas;

  @Transient
  private String            dtaCriacao;
  @Transient
  private String            dtaCancelamento;
  @Transient
  private String            dtaPagamento;
  @Transient
  private String            dtaVencimento;

  public String getDtaCriacao() {
    return dtaCriacao;
  }

  public void setDtaCriacao(String dtaCriacao) {
    this.dtaCriacao = dtaCriacao;
  }

  public String getDtaCancelamento() {
    return dtaCancelamento;
  }

  public void setDtaCancelamento(String dtaCancelamento) {
    this.dtaCancelamento = dtaCancelamento;
  }

  public String getDtaPagamento() {
    return dtaPagamento;
  }

  public void setDtaPagamento(String dtaPagamento) {
    this.dtaPagamento = dtaPagamento;
  }

  public BigDecimal getVlrTotalMultas() {
    return vlrTotalMultas;
  }

  public void setVlrTotalMultas(BigDecimal vlrTotalMultas) {
    this.vlrTotalMultas = vlrTotalMultas;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getFormaPagamento() {
    return formaPagamento;
  }

  public void setFormaPagamento(String formaPagamento) {
    this.formaPagamento = formaPagamento;
  }

  public String getPrazoPagamento() {
    return prazoPagamento;
  }

  public void setPrazoPagamento(String prazoPagamento) {
    this.prazoPagamento = prazoPagamento;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMotivoCancelamento() {
    return motivoCancelamento;
  }

  public void setMotivoCancelamento(String motivoCancelamento) {
    this.motivoCancelamento = motivoCancelamento;
  }

  public BigDecimal getVlrTotalDivida() {
    return vlrTotalDivida;
  }

  public void setVlrTotalDivida(BigDecimal vlrTotalDivida) {
    this.vlrTotalDivida = vlrTotalDivida;
  }

  public BigDecimal getVlrParcela() {
    return vlrParcela;
  }

  public void setVlrParcela(BigDecimal vlrParcela) {
    this.vlrParcela = vlrParcela;
  }

  public BigDecimal getVlrPago() {
    return vlrPago;
  }

  public void setVlrPago(BigDecimal vlrPago) {
    this.vlrPago = vlrPago;
  }

  public BigDecimal getVlrTxEntrega() {
    return vlrTxEntrega;
  }

  public void setVlrTxEntrega(BigDecimal vlrTxEntrega) {
    this.vlrTxEntrega = vlrTxEntrega;
  }

  public BigDecimal getVlrMulta() {
    return vlrMulta;
  }

  public void setVlrMulta(BigDecimal vlrMulta) {
    this.vlrMulta = vlrMulta;
  }

  public BigDecimal getVlrMultaExtravio() {
    return vlrMultaExtravio;
  }

  public void setVlrMultaExtravio(BigDecimal vlrMultaExtravio) {
    this.vlrMultaExtravio = vlrMultaExtravio;
  }

  public BigDecimal getVlrMultaAvaria() {
    return vlrMultaAvaria;
  }

  public void setVlrMultaAvaria(BigDecimal vlrMultaAvaria) {
    this.vlrMultaAvaria = vlrMultaAvaria;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(LocalDateTime dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  public LocalDateTime getDataVencimento() {
    return dataVencimento;
  }

  public void setDataVencimento(LocalDateTime dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

  public LocalDateTime getDataPagamento() {
    return dataPagamento;
  }

  public void setDataPagamento(LocalDateTime dataPagamento) {
    this.dataPagamento = dataPagamento;
  }

  public LocalDateTime getDataCancelamento() {
    return dataCancelamento;
  }

  public void setDataCancelamento(LocalDateTime dataCancelamento) {
    this.dataCancelamento = dataCancelamento;
  }

  public Boolean getPagamentoVencido() {
    return pagamentoVencido;
  }

  public void setPagamentoVencido(Boolean pagamentoVencido) {
    this.pagamentoVencido = pagamentoVencido;
  }

  public Boolean getPagamentoCancelado() {
    return pagamentoCancelado;
  }

  public void setPagamentoCancelado(Boolean pagamentoCancelado) {
    this.pagamentoCancelado = pagamentoCancelado;
  }

  public Boolean getEfetuouPagamento() {
    return efetuouPagamento;
  }

  public void setEfetuouPagamento(Boolean efetuouPagamento) {
    this.efetuouPagamento = efetuouPagamento;
  }

  public Long getTotalParcelas() {
    return totalParcelas;
  }

  public void setTotalParcelas(Long totalParcelas) {
    this.totalParcelas = totalParcelas;
  }

  public String getDtaVencimento() {
    return dtaVencimento;
  }

  public void setDtaVencimento(String dtaVencimento) {
    this.dtaVencimento = dtaVencimento;
  }

}
