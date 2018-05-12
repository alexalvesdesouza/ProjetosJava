package com.rmlocacoes.rmlocacoes.model;

import java.math.BigDecimal;
import java.util.List;

public class ContasAReceberDTO {

  private String       cliente;
  private Long         codigoCliente;
  private String       descricao;
  private BigDecimal   valorAReceber;
  private Boolean      vencido;
  private Boolean      pago;
  private String       dtaVencimento;
  private List<Pedido> pedidos;

  public ContasAReceberDTO() {
    super();
    // TODO Auto-generated constructor stub
  }

  public ContasAReceberDTO(String cliente, Long codigoCliente, String descricao, BigDecimal valorAReceber, Boolean vencido, Boolean pago,
      String dtaVencimento, List<Pedido> pedidos) {
    super();
    this.cliente = cliente;
    this.codigoCliente = codigoCliente;
    this.descricao = descricao;
    this.valorAReceber = valorAReceber;
    this.vencido = vencido;
    this.pago = pago;
    this.dtaVencimento = dtaVencimento;
    this.pedidos = pedidos;
  }

  public String getCliente() {
    return cliente;
  }

  public void setCliente(String cliente) {
    this.cliente = cliente;
  }

  public Long getCodigoCliente() {
    return codigoCliente;
  }

  public void setCodigoCliente(Long codigoCliente) {
    this.codigoCliente = codigoCliente;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public BigDecimal getValorAReceber() {
    return valorAReceber;
  }

  public void setValorAReceber(BigDecimal valorAReceber) {
    this.valorAReceber = valorAReceber;
  }

  public Boolean getVencido() {
    return vencido;
  }

  public void setVencido(Boolean vencido) {
    this.vencido = vencido;
  }

  public Boolean getPago() {
    return pago;
  }

  public void setPago(Boolean pago) {
    this.pago = pago;
  }

  public String getDtaVencimento() {
    return dtaVencimento;
  }

  public void setDtaVencimento(String dtaVencimento) {
    this.dtaVencimento = dtaVencimento;
  }

  public List<Pedido> getPedidos() {
    return pedidos;
  }

  public void setPedidos(List<Pedido> pedidos) {
    this.pedidos = pedidos;
  }

}
