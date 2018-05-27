package com.rmlocacoes.rmlocacoes.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Cliente;
import com.rmlocacoes.rmlocacoes.model.Endereco;
import com.rmlocacoes.rmlocacoes.model.Item;
import com.rmlocacoes.rmlocacoes.model.Pagamento;
import com.rmlocacoes.rmlocacoes.model.Pedido;
import com.rmlocacoes.rmlocacoes.repository.PedidoRepository;
import com.rmlocacoes.rmlocacoes.util.BussinessForbbidenException;
import com.rmlocacoes.rmlocacoes.util.JavaMailUtil;
import com.rmlocacoes.rmlocacoes.util.StatusPedido;
import com.rmlocacoes.rmlocacoes.util.TemplateEmail;
import com.rmlocacoes.rmlocacoes.util.UtilsData;
import com.rmlocacoes.rmlocacoes.validate.PedidoValidate;

@Service
public class PedidoService {

  private PedidoRepository     pedidoRepository;
  private PedidoValidate       entityValidate;
  private ItemService          itemService;
  private PagamentoService     pagamentoService;
  private ReceberService       receberService;
  private Map<String, Boolean> statusPedido;
  private JavaMailUtil         javaMail;

  @Autowired
  public PedidoService(PedidoRepository pedidoRepository, ItemService itemService, PagamentoService pagamentoService, PedidoValidate entityValidate,
      JavaMailUtil javaMail, ReceberService receberService) {
    this.pedidoRepository = pedidoRepository;
    this.itemService = itemService;
    this.pagamentoService = pagamentoService;
    this.entityValidate = entityValidate;
    this.javaMail = javaMail;
    this.receberService = receberService;
  }

  public Pedido buscaPedido(final Long codigo) {
    return this.pedidoRepository.findByCodigo(codigo);
  }

  private void loadStatusPedido(final Pedido pedido) {

    Boolean isPedidoFinalizado = pedido.getPedidoFinalizado() != null ? pedido.getPedidoFinalizado() : Boolean.FALSE;
    Boolean isPedidoCancelado = pedido.getPedidoCancelado() != null ? pedido.getPedidoCancelado() : Boolean.FALSE;
    Boolean isPedidoEntregue = pedido.getPedidoEntregue() != null ? pedido.getPedidoEntregue() : Boolean.FALSE;

    statusPedido = new HashMap<>();
    statusPedido.put(StatusPedido.CANCELADO.name(), isPedidoCancelado);
    statusPedido.put(StatusPedido.FINALIZADO.name(), isPedidoFinalizado);
    statusPedido.put(StatusPedido.ENTREGUE.name(), isPedidoEntregue);
  }

  public final Pedido registraEntregaPedido(Pedido pedido) {

    this.statusPermitidoAcao(pedido, StatusPedido.AGUARDANDO_ENTREGA.name());
    pedido.setStatus(StatusPedido.ENTREGUE.name());
    pedido.setPedidoEntregue(Boolean.TRUE);
    parseDatasPedido(pedido);
    pedido.setDataEntrega(LocalDateTime.now());
    Pedido saved = this.pedidoRepository.saveAndFlush(pedido);
    this.receberService.geraRecebimentoPedido(saved);
    return saved;
  }

  private void statusPermitidoAcao(Pedido pedido, String status) {
    String statusPedido = pedido.getStatus();
    statusPedido = statusPedido.replaceAll(" ", "_");
    if (!statusPedido.equals(status)) {
      throw new BussinessForbbidenException();
    }
  }

  public final Pedido finalizarPedido(Pedido pedido) {
    this.statusPermitidoAcao(pedido, StatusPedido.ENTREGUE.name());
    pedido.setStatus(StatusPedido.FINALIZADO.name());
    pedido.setPedidoFinalizado(Boolean.TRUE);
    parseDatasPedido(pedido);
    pedido.setDataDevolucao(LocalDateTime.now());
    Pedido saved = this.pedidoRepository.saveAndFlush(pedido);
    this.receberService.geraRecebimentoPedido(saved);
    return saved;
  }

  public Boolean registraEntregaPedidosEmMassa(final List<Pedido> pedidos) {

    try {
      pedidos.forEach(pedido -> {
        this.registraEntregaPedido(pedido);
      });
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  public Boolean registraDevolucaoPedidosEmMassa(final List<Pedido> pedidos) {
    try {
      pedidos.forEach(pedido -> {
        this.finalizarPedido(pedido);
      });
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  public final Pedido marcaPedidoComoPago(Pedido pedido) {
    pagamentoService.efetuaPagamento(pedido.getPagamento());
    return this.pedidoRepository.saveAndFlush(pedido);
  }

  public Pedido salvaPedido(final Pedido pedido) {

    this.loadStatusPedido(pedido);
    Boolean isPedidoFinalizado = statusPedido.get(StatusPedido.FINALIZADO.name());
    Boolean isPedidoCancelado = statusPedido.get(StatusPedido.CANCELADO.name());
    Boolean isPedidoEntregue = statusPedido.get(StatusPedido.ENTREGUE.name());

    parseDatasPedido(pedido);
    this.entityValidate.validaPedido(pedido);

    pedido.getItens()
          .forEach(item -> {
            itemService.salvaItem(item, this.statusPedido);
          });

    BigDecimal totalPedido = pedido.getItens()
                                   .stream()
                                   .map(Item::getTotalItem)
                                   .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (pedido.getPagamento() != null) {

      Pagamento pagamento = pedido.getPagamento();
      pagamentoService.salvaPagamento(pagamento);

    }

    BigDecimal vlrTxEntrega = pedido.getPagamento()
                                    .getVlrTxEntrega();

    totalPedido = vlrTxEntrega.add(totalPedido);

    String statusPedido = null;
    if (isPedidoFinalizado) {
      statusPedido = StatusPedido.FINALIZADO.name()
                                            .replace("_", " ");
      pedido.setDataDevolucao(LocalDateTime.now());
    } else if (isPedidoCancelado) {

      statusPedido = StatusPedido.CANCELADO.name()
                                           .replace("_", " ");
      pedido.setDataCancelamento(LocalDateTime.now());
    } else if (isPedidoEntregue) {

      statusPedido = StatusPedido.ENTREGUE.name()
                                          .replace("_", " ");
      pedido.setDataEntrega(LocalDateTime.now());
    }

    else {
      statusPedido = StatusPedido.AGUARDANDO_ENTREGA.name()
                                                    .replace("_", " ");
    }

    pedido.setStatus(statusPedido);
    pedido.setTotalPedido(totalPedido);
    if (null == pedido.getPedidoEntregue())
      pedido.setPedidoEntregue(false);

    if (null == pedido.getPedidoCancelado())
      pedido.setPedidoCancelado(false);

    if (null == pedido.getPedidoFinalizado())
      pedido.setPedidoFinalizado(false);

    if (null == pedido.getDataEntrega())
      pedido.setDataEntrega(LocalDateTime.now());

    if (null == pedido.getDataDevolucao())
      pedido.setDataDevolucao(LocalDateTime.now()
                                           .plusDays(3L));

    Pedido save = pedidoRepository.save(pedido);
    this.gerarContrato(save);
    return save;
  }

  private void gerarContrato(Pedido pedido) {
    TemplateEmail template = new TemplateEmail();
    pedido.setContratoImpressao(template.getTemplateContrato());
    this.enviaEmailPedido(pedido);
  }

  private void enviaEmailPedido(Pedido pedido) {
    String titulo = "Pedido: " + pedido.getCodigo();
    String msg = pedido.getContratoImpressao();

    final Cliente cliente = pedido.getCliente();
    final String nomeCliente = cliente.getNome();
    final String telefoneCelular = cliente.getTelefoneCelular();
    final String telefoneFixo = cliente.getTelefoneCelular();

    final String tipoDoc = cliente.getCpf() != null ? "CPF" : "CNPJ";
    final String documento = cliente.getCpf() != null ? cliente.getCpf() : cliente.getCnpj();
    final List<Endereco> enderecos = cliente.getEnderecos();
    final Optional<Endereco> enderecoEntrega = enderecos.stream()
                                                        .filter(e -> e.getEnderecoEntrega())
                                                        .findFirst();

    final String enderecoEntregar = enderecoEntrega.get()
                                                   .getLogradouro()
                                                   .concat(" n°")
                                                   .concat(enderecoEntrega.get()
                                                                          .getNumero());
    final String bairroLocatario = enderecoEntrega.get()
                                                  .getBairro();

    final List<Item> itens = pedido.getItens();
    final Pagamento pagamento = pedido.getPagamento();
    BigDecimal frete = pagamento.getVlrTxEntrega();
    BigDecimal vlrTotalFinal = pedido.getTotalPedido();

    BigDecimal vlrTotalComodato = itens.stream()
                                       .filter(item -> item.getProduto()
                                                           .getCategoria()
                                                           .equals("COMODATO"))
                                       .map(Item::getTotalItem)
                                       .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal vlrTotalRevenda = itens.stream()
                                      .filter(item -> item.getProduto()
                                                          .getCategoria()
                                                          .equals("REVENDA"))
                                      .map(Item::getTotalItem)
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);


    StringBuffer sb = new StringBuffer();
    itens.forEach(item -> {

      sb.append("<tr  style='padding: 10px 5px; text-align: center;'>")
        .append("<td>")
        .append(item.getProduto()
                    .getNome())
        .append("</td>")
        .append("<td>")
        .append(item.getProduto()
                    .getPreco())
        .append("</td>")
        .append("<td>")
        .append(item.getQuantidade())
        .append("</td>")
        .append("<td>")
        .append(item.getTotalItem())
        .append("</td>")
        .append("</tr>");

    });

    LocalDateTime dtaEntrega = pedido.getDataEntrega();
    LocalDateTime dtaDevolucao = pedido.getDataDevolucao();

    msg = msg.replace("locatario.cliente.nome", nomeCliente);
    msg = msg.replace("locatario.cliente.documento", documento);
    msg = msg.replace("locatario.enderecoContrato.logradouro", enderecoEntregar);
    msg = msg.replace("locatario.enderecoContrato.bairro", bairroLocatario);
    msg = msg.replace("locatario.cliente.teleFoneCelular", telefoneCelular);
    msg = msg.replace("locatario.dtaEntrega", UtilsData.getDateTimeShort(dtaEntrega));
    msg = msg.replace("locatario.dtaDevolucao", UtilsData.getDateTimeShort(dtaDevolucao));
    msg = msg.replace("contrato.pagamento.vlrTxEntrega", frete.toString());
    msg = msg.replace("contrato.totalProdutos", vlrTotalRevenda.toString());
    msg = msg.replace("contrato.totalComodato", vlrTotalComodato.toString());
    msg = msg.replace("dtaContrato", UtilsData.getDateTimeShort(LocalDateTime.now()));
    msg = msg.replace("contrato.totalPedido", vlrTotalFinal.toString());
    msg = msg.replace("{{itens}}", sb.toString());
    msg = msg.replace("{{tipoDoc}}", tipoDoc);
    msg = msg.replace("{{locatario.cliente.teleFoneFixo}}", telefoneFixo != null ? " - ".concat(telefoneFixo) : "");

    javaMail.sendEmail(msg, titulo);

  }

  private void parseDatasPedido(Pedido pedido) {

    final String dtaEntrega = pedido.getDtaEntrega();
    final String dtaDevolucao = pedido.getDtaDevolucao();
    final String dtaCancelamento = pedido.getDtaCancelamento();
    if (dtaEntrega != null && !dtaEntrega.isEmpty()) {
      LocalDateTime dateTimeFormatted = UtilsData.getDateTimeFormatted(dtaEntrega, "/");
      pedido.setDataEntrega(dateTimeFormatted);
    }
    if (dtaDevolucao != null && !dtaDevolucao.isEmpty()) {
      LocalDateTime dateTimeFormatted = UtilsData.getDateTimeFormatted(dtaDevolucao, "/");
      pedido.setDataDevolucao(dateTimeFormatted);
    }
    if (dtaCancelamento != null && !dtaCancelamento.isEmpty()) {
      LocalDateTime dateTimeFormatted = UtilsData.getDateTimeFormatted(dtaCancelamento, "/");
      pedido.setDataCancelamento(dateTimeFormatted);
    }
    pedido.setDataPedido(LocalDateTime.now());

  }

  public List<Pedido> getPedidos(Long page) {
    return this.pedidoRepository.findAllPedidos(page);
  }

  public List<Pedido> getPedidos(String statusPedido) {
    List<Pedido> findAll = this.pedidoRepository.findAll();

    if ("pedidos-entregues".equals(statusPedido)) {
      findAll = this.pedidoRepository.findAll()
                                     .stream()
                                     .filter(pedido -> StatusPedido.ENTREGUE.name()
                                                                            .equals(pedido.getStatus()))
                                     .collect(Collectors.toList());
    } else if ("pedidos-efetuados".equals(statusPedido)) {
      findAll = findAll.stream()
                       .filter(pedido -> StatusPedido.AGUARDANDO_ENTREGA.name()
                                                                        .equals(pedido.getStatus()
                                                                                      .replaceAll(" ", "_")))
                       .collect(Collectors.toList());
    } else {
      findAll = this.pedidoRepository.findAll()
                                     .stream()
                                     .filter(pedido -> StatusPedido.FINALIZADO.name()
                                                                              .equals(pedido.getStatus()))
                                     .collect(Collectors.toList());
    }

    findAll.sort((Pedido p1, Pedido p2) -> p1.getStatus()
                                             .compareTo(p2.getStatus()));

    findAll.forEach(pedido -> {
      LocalDateTime dataEntrega = pedido.getDataEntrega();
      if (dataEntrega != null) {
        String data = UtilsData.getLocalDateTimeToString(dataEntrega, "US", "dd/mm/yyyy");
        pedido.setDtaEntrega(data);
      }
      LocalDateTime dataDevolucao = pedido.getDataDevolucao();
      if (dataDevolucao != null) {
        String data = UtilsData.getLocalDateTimeToString(dataDevolucao, "US", "dd/mm/yyyy");
        pedido.setDtaDevolucao(data);
      }
      LocalDateTime dataPedido = pedido.getDataPedido();
      if (dataPedido != null) {
        String data = UtilsData.getLocalDateTimeToString(dataPedido, "US", "dd/mm/yyyy");
        pedido.setDtaPedido(data);
      }
      LocalDateTime dataCancelamento = pedido.getDataCancelamento();
      if (dataCancelamento != null) {
        String data = UtilsData.getLocalDateTimeToString(dataCancelamento, "US", "dd/mm/yyyy");
        pedido.setDtaCancelamento(data);
      }
      Pagamento pagamento = pedido.getPagamento();
      if (null != pagamento) {
        pagamentoService.getMetricasPagamento(pagamento, pedido.getTotalPedido());
      }

    });
    return findAll;
  }

}
