package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rm_item")
public class Item implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;
  private Long              quantidade;
  private BigDecimal        desconto;
  private BigDecimal        totalItem;
  private BigDecimal        vlrUnitario;

  @OneToOne
  private Produto           produto;
  @ManyToOne
  private Pedido            pedido;

  public BigDecimal getVlrUnitario() {
    return vlrUnitario;
  }

  public void setVlrUnitario(BigDecimal vlrUnitario) {
    this.vlrUnitario = vlrUnitario;
  }

  public BigDecimal getTotalItem() {
    return totalItem;
  }

  public void setTotalItem(BigDecimal totalItem) {
    this.totalItem = totalItem;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public Long getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Long quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getDesconto() {
    return desconto;
  }

  public void setDesconto(BigDecimal desconto) {
    this.desconto = desconto;
  }

  public Pedido getPedido() {
    return pedido;
  }

  public void setPedido(Pedido pedido) {
    this.pedido = pedido;
  }

}
