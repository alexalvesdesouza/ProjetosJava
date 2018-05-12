package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rm_cliente")
public class Cliente implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;

  private LocalDateTime     dataCadastro;
  private LocalDateTime     dataNascimento;

  @Transient
  private String            dtaNascimento;
  private Boolean           ativo;
  private Boolean           pessoaJuridica;
  private String            nome;
  private String            razaoSocial;
  private String            email;
  private String            cpf;
  private String            cnpj;
  private String            rg;
  private String            inscricaoEstadual;
  private String            telefoneFixo;
  private String            telefoneCelular;
  
  @OneToOne
  private DadosBancarios    dadosBancarios;

  @ManyToMany
  private List<Contato>     contatos;

  @OneToMany
  private List<Endereco>    enderecos;

  public String getDtaNascimento() {
    return dtaNascimento;
  }

  public void setDtaNascimento(String dtaNascimento) {
    this.dtaNascimento = dtaNascimento;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public LocalDateTime getDataCadastro() {
    return dataCadastro;
  }

  public void setDataCadastro(LocalDateTime dataCadastro) {
    this.dataCadastro = dataCadastro;
  }

  public LocalDateTime getDataNascimento() {
    return dataNascimento;
  }

  public void setDataNascimento(LocalDateTime dataNascimento) {
    this.dataNascimento = dataNascimento;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public Boolean getPessoaJuridica() {
    return pessoaJuridica;
  }

  public void setPessoaJuridica(Boolean pessoaJuridica) {
    this.pessoaJuridica = pessoaJuridica;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getRazaoSocial() {
    return razaoSocial;
  }

  public void setRazaoSocial(String razaoSocial) {
    this.razaoSocial = razaoSocial;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getRg() {
    return rg;
  }

  public void setRg(String rg) {
    this.rg = rg;
  }

  public String getInscricaoEstadual() {
    return inscricaoEstadual;
  }

  public void setInscricaoEstadual(String inscricaoEstadual) {
    this.inscricaoEstadual = inscricaoEstadual;
  }

  public String getTelefoneFixo() {
    return telefoneFixo;
  }

  public void setTelefoneFixo(String telefoneFixo) {
    this.telefoneFixo = telefoneFixo;
  }

  public String getTelefoneCelular() {
    return telefoneCelular;
  }

  public void setTelefoneCelular(String telefoneCelular) {
    this.telefoneCelular = telefoneCelular;
  }

  public DadosBancarios getDadosBancarios() {
    return dadosBancarios;
  }

  public void setDadosBancarios(DadosBancarios dadosBancarios) {
    this.dadosBancarios = dadosBancarios;
  }

  public List<Contato> getContatos() {
    return contatos;
  }

  public void setContatos(List<Contato> contatos) {
    this.contatos = contatos;
  }

  public List<Endereco> getEnderecos() {
    return enderecos;
  }

  public void setEnderecos(List<Endereco> enderecos) {
    this.enderecos = enderecos;
  }

}
