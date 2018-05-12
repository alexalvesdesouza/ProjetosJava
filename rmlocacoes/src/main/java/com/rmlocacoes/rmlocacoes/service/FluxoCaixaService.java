package com.rmlocacoes.rmlocacoes.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Cliente;
import com.rmlocacoes.rmlocacoes.model.ContasAReceberDTO;
import com.rmlocacoes.rmlocacoes.model.ContaVinculada;
import com.rmlocacoes.rmlocacoes.model.Pagar;
import com.rmlocacoes.rmlocacoes.model.Pedido;
import com.rmlocacoes.rmlocacoes.model.Receber;
import com.rmlocacoes.rmlocacoes.util.StatusEntidade;

@Service
public class FluxoCaixaService {

  private PedidoService                   pedidoService;
  private ReceberService                  receberService;
  private PagarService                    pagarService;
  private ContaVinculadaService           contaVinculadaService;
  private Map<Cliente, ContasAReceberDTO> clientesMap = new HashMap<>();

  @Autowired
  public FluxoCaixaService(PedidoService pedidoService, PagarService pagarService, ContaVinculadaService contaVinculadaService,
      ReceberService receberService) {
    super();
    this.pedidoService = pedidoService;
    this.pagarService = pagarService;
    this.contaVinculadaService = contaVinculadaService;
    this.receberService = receberService;
  }

  public Collection<ContasAReceberDTO> loadPedidos(Long page) {

    pedidoService.getPedidos(page)
                 .forEach(pedido -> {
                   this.deparaContasAReceber(pedido);
                 });

    Collection<ContasAReceberDTO> values = clientesMap.values();

    return values;
  }

  private void deparaContasAReceber(Pedido pedido) {

    Cliente cliente = pedido.getCliente();

    if (!clientesMap.containsKey(cliente)) {
      ContasAReceberDTO receber = new ContasAReceberDTO(cliente.getNome(),
                                                        cliente.getCodigo(),
                                                        "Pedido - " + pedido.getCodigo(),
                                                        pedido.getTotalPedido(),
                                                        pedido.getPagamento()
                                                              .getEfetuouPagamento(),
                                                        pedido.getPagamento()
                                                              .getPagamentoVencido(),
                                                        pedido.getPagamento()
                                                              .getDtaVencimento(),
                                                        Arrays.asList(pedido));
      clientesMap.put(cliente, receber);
    } else {

      BigDecimal valorAReceber = clientesMap.get(cliente)
                                            .getValorAReceber();
      BigDecimal totalPedido = pedido.getTotalPedido();
      BigDecimal totalFinal = valorAReceber.add(totalPedido);

      ContasAReceberDTO contasAReceberDTO = clientesMap.get(cliente);
      List<Pedido> pedidos = new ArrayList<>();
      contasAReceberDTO.getPedidos()
                       .forEach(pedido1 -> {
                         pedidos.add(pedido1);

                       });
      pedidos.add(pedido);
      contasAReceberDTO.setPedidos(pedidos);
      contasAReceberDTO.setValorAReceber(totalFinal);
      clientesMap.put(cliente, contasAReceberDTO);
    }

  }

  public void marcarPedidoComoPago(Long codigo) {
    Pedido pedido = this.pedidoService.buscaPedido(codigo);
    this.pedidoService.marcaPedidoComoPago(pedido);
  }

  public Map<String, Map<String, Object>> loadConsolidados() {

    Map<String, Map<String, Object>> consolidados = new HashMap<>();
    consolidados.put("receber", this.loadConsolidadoReceber());
    consolidados.put("pagar", this.loadConsolidadoPagar());
    consolidados.put("contas", this.loadContasVinculadas());

    return consolidados;
  }

  private Map<String, Object> loadContasVinculadas() {
    Map<String, Object> map = new HashMap<>();
    List<ContaVinculada> contasVinculadas = this.contaVinculadaService.getContasVinculadas();
    map.put("vinculadas", contasVinculadas);
    return map;
  }

  private Map<String, Object> loadConsolidadoPagar() {
    List<Pagar> contasAPagar = pagarService.getContasAPagar();

    Map<String, Object> map = new HashMap<>();

    BigDecimal motanteTotalDespesas = contasAPagar.stream()
                                                  .map(Pagar::getValor)
                                                  .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalContasPagas = new BigDecimal(BigDecimal.ZERO.toString());

    map.put("total", 0);
    map.put("pagos", 0);
    map.put("diferenca", 0);

    totalContasPagas = contasAPagar.stream()
                                   .filter(contaPagar -> contaPagar.getStatus()
                                                                   .equals(StatusEntidade.PAGO.name()))
                                   .map(Pagar::getValor)
                                   .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal percentDiferenca = new BigDecimal(BigDecimal.ZERO.toString()).setScale(0, RoundingMode.HALF_EVEN);
    BigDecimal percentualTotalContasPagas = totalContasPagas.multiply(new BigDecimal("100"));

    percentDiferenca = percentualTotalContasPagas.divide(motanteTotalDespesas, 0, RoundingMode.HALF_EVEN);

    map.put("total", motanteTotalDespesas);
    map.put("pagos", totalContasPagas);
    map.put("diferenca", percentDiferenca);

    return map;
  }

  private Map<String, Object> loadConsolidadoReceber() {

    List<Receber> receber = this.receberService.getRecebimentos();

    Map<String, Object> map = new HashMap<>();
    BigDecimal totalContasReceber = receber.stream()
                                           .map(Receber::getValor)
                                           .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigDecimal totalContasRecebidas = new BigDecimal(BigDecimal.ZERO.toString());

    map.put("total", 0);
    map.put("pagos", 0);
    map.put("diferenca", 0);

    if (totalContasReceber.compareTo(BigDecimal.ZERO) != 0) {

      totalContasRecebidas = receber.stream()
                                    .filter(rec -> rec.getStatus()
                                                      .equals(StatusEntidade.RECEBIDO.name()))
                                    .map(Receber::getValor)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal dif = new BigDecimal(BigDecimal.ZERO.toString());

      dif = totalContasRecebidas.multiply(new BigDecimal("100"));
      dif = dif.divide(totalContasReceber, 0, RoundingMode.HALF_EVEN);
      map.put("total", totalContasReceber);
      map.put("pagos", totalContasRecebidas);
      map.put("diferenca", dif);
    }

    return map;

  }

}
