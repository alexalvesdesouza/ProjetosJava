package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "rm_convidado")
public class Convidado implements Serializable {

  /**
   * 
   */
  private static final long  serialVersionUID = 1L;

  public static final String CAMPO_RG         = "O campo RG não pode ser vazio.";
  public static final String CAMPO_NOME       = "O Campo Nome não pode ser vazio.";

  @Id
  @NotEmpty(message = CAMPO_RG)
  private String             rg;
  @NotEmpty(message = CAMPO_NOME)
  private String             nomeConvidado;

  @ManyToOne
  private Evento             evento;

  public String getRg() {
    return rg;
  }

  public void setRg(String rg) {
    this.rg = rg;
  }

  public String getNomeConvidado() {
    return nomeConvidado;
  }

  public void setNomeConvidado(String nomeConvidado) {
    this.nomeConvidado = nomeConvidado;
  }

  public Evento getEvento() {
    return evento;
  }

  public void setEvento(Evento evento) {
    this.evento = evento;
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return super.toString();
  }

}
