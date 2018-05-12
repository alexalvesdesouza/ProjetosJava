package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rmlocacoes.rmlocacoes.util.LocalDateDeserializer;
import com.rmlocacoes.rmlocacoes.util.LocalDateSerializer;

@Entity
@Table(name = "rm_contas_a_pagar")
public class Pagar implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;

  private String            descricao;
  private BigDecimal        valor;
  private String            tipoDespesa;
  private String            numeroDocumento;
  private String            observacao;
  private String            status;
  
  @Transient
  private String            dtaVencimento;

  private LocalDate         dataPagamento;
  private LocalDate         dataCadastro;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate         dataVencimento;

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getTipoDespesa() {
    return tipoDespesa;
  }

  public void setTipoDespesa(String tipoDespesa) {
    this.tipoDespesa = tipoDespesa;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
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

  public String getDtaVencimento() {
    return dtaVencimento;
  }

  public void setDtaVencimento(String dtaVencimento) {
    this.dtaVencimento = dtaVencimento;
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

  public LocalDate getDataVencimento() {
    return dataVencimento;
  }

  public void setDataVencimento(LocalDate dataVencimento) {
    this.dataVencimento = dataVencimento;
  }

}
