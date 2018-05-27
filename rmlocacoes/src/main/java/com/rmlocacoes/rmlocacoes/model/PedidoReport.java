package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rm_pedido_report")
public class PedidoReport implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Id
  private String            chaveDiaria;
  
  

  public PedidoReport() {
    super();
  }

  public PedidoReport(String chaveDiaria) {
    super();
    this.chaveDiaria = chaveDiaria;
  }

  public String getChaveDiaria() {
    return chaveDiaria;
  }

  public void setChaveDiaria(String chaveDiaria) {
    this.chaveDiaria = chaveDiaria;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((chaveDiaria == null) ? 0 : chaveDiaria.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PedidoReport other = (PedidoReport) obj;
    if (chaveDiaria == null) {
      if (other.chaveDiaria != null)
        return false;
    } else if (!chaveDiaria.equals(other.chaveDiaria))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PedidoReport [chaveDiaria=" + chaveDiaria + "]";
  }

}
