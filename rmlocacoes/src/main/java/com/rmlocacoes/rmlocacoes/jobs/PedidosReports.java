package com.rmlocacoes.rmlocacoes.jobs;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rmlocacoes.rmlocacoes.model.Endereco;
import com.rmlocacoes.rmlocacoes.model.Pedido;
import com.rmlocacoes.rmlocacoes.service.PedidoReportService;
import com.rmlocacoes.rmlocacoes.service.PedidoService;
import com.rmlocacoes.rmlocacoes.util.EncryptToMD5;
import com.rmlocacoes.rmlocacoes.util.JavaMailUtil;
import com.rmlocacoes.rmlocacoes.util.UtilsData;

@Component
@EnableScheduling
public class PedidosReports {

  private final Logger              logger        = LoggerFactory.getLogger(PedidosReports.class);
  private final long                SEGUNDO       = 1000;
  private final long                MINUTO        = SEGUNDO * 60;
  private final long                HORA          = MINUTO * 60;
  private final String              STATUS_PEDIDO = "pedidos-entregues";
  private final String              ASSUNTO       = "Pedidos atrasados para devolução";

  private final PedidoService       pedidoService;
  private final PedidoReportService reportService;
  private final JavaMailUtil        javaMail;

  @Autowired
  public PedidosReports(PedidoService pedidoService, PedidoReportService reportService, JavaMailUtil javaMail) {
    this.pedidoService = pedidoService;
    this.javaMail = javaMail;
    this.reportService = reportService;
  }

  @Scheduled(fixedDelay = MINUTO)
  private void reportaPedidosPendentesDevolucao() throws NoSuchAlgorithmException {

    String chaveDiaria = LocalDate.now()
                                  .toString()
                                  .concat(STATUS_PEDIDO);

    final String chave = EncryptToMD5.converterParaMD5(chaveDiaria);

    if (!reportService.isChaveExistente(chave)) {
      List<Pedido> pedidos = pedidoService.getPedidos(STATUS_PEDIDO)
                                          .stream()
                                          .filter(pedido -> LocalDateTime.now()
                                                                         .getDayOfMonth() > pedido.getDataDevolucao()
                                                                                                  .getDayOfMonth())
                                          .collect(Collectors.toList());

      javaMail.sendEmail(formataMsgPedidos(pedidos), ASSUNTO);
      reportService.insereChaveDiaria(chave);
      logger.info("NOTIFICACAO CHAVE (" + chave + " ) ENVIADA POR EMAIL EM :" + LocalDate.now());
    }

  }

  private final String formataMsgPedidos(final List<Pedido> pedidosPendentes) {
    StringBuffer sb = new StringBuffer();

    sb.append("<table border='1 px solid'>")
      .append("<th>N°</th>")
      .append("<th>Status Pedido</th>")
      .append("<th>Data Entrega</th>")
      .append("<th>Data prevista Devolução</th>")
      .append("<th>Pedido Atrazado ?</th>")
      .append("<th>Cliente</th>")
      .append("<th>Telefone</th>")
      .append("<th>Endereço</th>");

    pedidosPendentes.forEach(pedido -> {
      Endereco endereco = null;
      String logradouro = null;
      String numeroLogradouro = null;
      String bairro = null;
      String enderecoFormatado = null;
      //
      // List<Endereco> enderecos = pedido.getCliente()
      // .getEnderecos();
      //
      // if (!enderecos.isEmpty()) {
      // endereco = pedido.getCliente()
      // .getEnderecos()
      // .get(0);
      // logradouro = endereco.getLogradouro();
      // numeroLogradouro = endereco.getNumero();
      // bairro = endereco.getBairro();
      // enderecoFormatado = logradouro + " N° " + numeroLogradouro + " Bairro" + bairro;
      // }

      String dataEntrega = UtilsData.getLocalDateTimeToString(pedido.getDataEntrega(), "US", "dd/mm/yyyy");
      pedido.setDtaEntrega(dataEntrega);
      String dataDevolucao = UtilsData.getLocalDateTimeToString(pedido.getDataDevolucao(), "US", "dd/mm/yyyy");
      pedido.setDtaDevolucao(dataDevolucao);

      sb.append("<tr  style='padding: 10px 5px; text-align: center;'>")
        .append("<td>")
        .append(pedido.getCodigo())
        .append("</td>")

        .append("<td>")
        .append(pedido.getStatus())
        .append("</td>")
        .append("<td>")
        .append(pedido.getDtaEntrega())
        .append("</td>")
        .append("<td>")
        .append(pedido.getDtaDevolucao())
        .append("</td>")

        .append("<td>")
        .append(LocalDateTime.now()
                             .getDayOfMonth() > pedido.getDataDevolucao()
                                                      .getDayOfMonth() ? "Sim" : "Não")
        .append("</td>")

        .append("<td>")
        .append(pedido.getCliente()
                      .getNome())
        .append("</td>")

        .append("<td>")
        .append(pedido.getCliente()
                      .getTelefoneCelular())
        .append("</td>")
        .append("<td>")
        .append(null != enderecoFormatado ? enderecoFormatado : "")
        .append("</td>")

        .append("</tr>");

    });
    sb.append("</table>");
    return sb.toString();
  }

}
