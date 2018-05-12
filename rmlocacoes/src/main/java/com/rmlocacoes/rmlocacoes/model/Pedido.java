package com.rmlocacoes.rmlocacoes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rm_pedido")
public class Pedido implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;

  private LocalDateTime     dataPedido;
  private LocalDateTime     dataEntrega;
  private LocalDateTime     dataDevolucao;
  private LocalDateTime     dataCancelamento;
  private String            observacao;
  private Boolean           devolucaoParcial;
  private Boolean           devolucaoAvariada;
  private Boolean           pedidoCancelado;
  private Boolean           pedidoFinalizado;
  private Boolean           pedidoEntregue;
  private BigDecimal        totalPedido;
  private BigDecimal        desconto;
  private String            status;

  @Transient
  private String            dtaEntrega;
  @Transient
  private String            dtaDevolucao;
  @Transient
  private String            dtaCancelamento;
  @Transient
  private String            dtaPedido;
  @Transient
  private String            contratoImpressao;

  @OneToOne
  private Cliente           cliente;

  @OneToOne
  private Pagamento         pagamento;

  @OneToMany
  private List<Item>        itens;

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public LocalDateTime getDataPedido() {
    return dataPedido;
  }

  public void setDataPedido(LocalDateTime dataPedido) {
    this.dataPedido = dataPedido;
  }

  public LocalDateTime getDataEntrega() {
    return dataEntrega;
  }

  public void setDataEntrega(LocalDateTime dataEntrega) {
    this.dataEntrega = dataEntrega;
  }

  public LocalDateTime getDataDevolucao() {
    return dataDevolucao;
  }

  public void setDataDevolucao(LocalDateTime dataDevolucao) {
    this.dataDevolucao = dataDevolucao;
  }

  public LocalDateTime getDataCancelamento() {
    return dataCancelamento;
  }

  public void setDataCancelamento(LocalDateTime dataCancelamento) {
    this.dataCancelamento = dataCancelamento;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public Boolean getDevolucaoParcial() {
    return devolucaoParcial;
  }

  public void setDevolucaoParcial(Boolean devolucaoParcial) {
    this.devolucaoParcial = devolucaoParcial;
  }

  public Boolean getDevolucaoAvariada() {
    return devolucaoAvariada;
  }

  public void setDevolucaoAvariada(Boolean devolucaoAvariada) {
    this.devolucaoAvariada = devolucaoAvariada;
  }

  public Boolean getPedidoCancelado() {
    return pedidoCancelado;
  }

  public void setPedidoCancelado(Boolean pedidoCancelado) {
    this.pedidoCancelado = pedidoCancelado;
  }

  public Boolean getPedidoFinalizado() {
    return pedidoFinalizado;
  }

  public void setPedidoFinalizado(Boolean pedidoFinalizado) {
    this.pedidoFinalizado = pedidoFinalizado;
  }

  public BigDecimal getTotalPedido() {
    return totalPedido;
  }

  public void setTotalPedido(BigDecimal totalPedido) {
    this.totalPedido = totalPedido;
  }

  public BigDecimal getDesconto() {
    return desconto;
  }

  public void setDesconto(BigDecimal desconto) {
    this.desconto = desconto;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDtaEntrega() {
    return dtaEntrega;
  }

  public void setDtaEntrega(String dtaEntrega) {
    this.dtaEntrega = dtaEntrega;
  }

  public String getDtaDevolucao() {
    return dtaDevolucao;
  }

  public void setDtaDevolucao(String dtaDevolucao) {
    this.dtaDevolucao = dtaDevolucao;
  }

  public String getDtaCancelamento() {
    return dtaCancelamento;
  }

  public void setDtaCancelamento(String dtaCancelamento) {
    this.dtaCancelamento = dtaCancelamento;
  }

  public String getDtaPedido() {
    return dtaPedido;
  }

  public void setDtaPedido(String dtaPedido) {
    this.dtaPedido = dtaPedido;
  }

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public Pagamento getPagamento() {
    return pagamento;
  }

  public void setPagamento(Pagamento pagamento) {
    this.pagamento = pagamento;
  }

  public List<Item> getItens() {
    return itens;
  }

  public void setItens(List<Item> itens) {
    this.itens = itens;
  }

  public Boolean getPedidoEntregue() {
    return pedidoEntregue;
  }

  public void setPedidoEntregue(Boolean pedidoEntregue) {
    this.pedidoEntregue = pedidoEntregue;
  }

  public String getContratoImpressao() {
    return contratoImpressao;
  }

  public void setContratoImpressao(String contratoImpressao) {
    this.contratoImpressao = contratoImpressao;
  }

}
