package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rm_endereco")
public class Endereco implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;

  private String            logradouro;
  private String            cep;
  private String            numero;
  private String            bairro;
  private String            cidade;
  private String            estado;
  private String            uf;
  private String            complemento;
  private Boolean           enderecoEntrega;
  private Boolean           enderecoComercial;
  private String            funcInicio;
  private String            funcFim;
  private String            observacao;

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getLogradouro() {
    return logradouro;
  }

  public void setLogradouro(String logradouro) {
    this.logradouro = logradouro;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getBairro() {
    return bairro;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getUf() {
    return uf;
  }

  public void setUf(String uf) {
    this.uf = uf;
  }

  public String getComplemento() {
    return complemento;
  }

  public void setComplemento(String complemento) {
    this.complemento = complemento;
  }

  public Boolean getEnderecoEntrega() {
    return enderecoEntrega;
  }

  public void setEnderecoEntrega(Boolean enderecoEntrega) {
    this.enderecoEntrega = enderecoEntrega;
  }

  public Boolean getEnderecoComercial() {
    return enderecoComercial;
  }

  public void setEnderecoComercial(Boolean enderecoComercial) {
    this.enderecoComercial = enderecoComercial;
  }

  public String getFuncInicio() {
    return funcInicio;
  }

  public void setFuncInicio(String funcInicio) {
    this.funcInicio = funcInicio;
  }

  public String getFuncFim() {
    return funcFim;
  }

  public void setFuncFim(String funcFim) {
    this.funcFim = funcFim;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

}
