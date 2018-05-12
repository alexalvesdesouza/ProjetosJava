package com.rmlocacoes.rmlocacoes.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Pagamento;
import com.rmlocacoes.rmlocacoes.repository.PagamentoRepository;
import com.rmlocacoes.rmlocacoes.util.StatusPagamento;
import com.rmlocacoes.rmlocacoes.util.UtilsData;
import com.rmlocacoes.rmlocacoes.validate.PagamentoValidate;

@Service
public class PagamentoService {

  private PagamentoRepository repository;
  private PagamentoValidate   pagamentoValidate;
  private static final String PAGO    = "PAGO";
  private static final String A_PAGAR = "A PAGAR";

  @Autowired
  public PagamentoService(PagamentoRepository repository, PagamentoValidate pagamentoValidate) {
    this.repository = repository;
    this.pagamentoValidate = pagamentoValidate;
  }

  public Pagamento salvaPagamento(Pagamento entity) {

    parseDatasPagamento(entity);
    this.pagamentoValidate.validaPagamento(entity);
    String status = PAGO;
    if (entity.getEfetuouPagamento()) {
      status = PAGO;
    } else {
      status = A_PAGAR;
    }
    entity.setStatus(status);
    Pagamento save = repository.save(entity);
    return save;
  }

  private void parseDatasPagamento(Pagamento entity) {

    final String dtaVencimento = entity.getDtaVencimento();
    if (dtaVencimento != null && !dtaVencimento.isEmpty()) {
      LocalDateTime dateTimeFormatted = UtilsData.getDateTimeFormatted(dtaVencimento, "/");
      entity.setDataVencimento(dateTimeFormatted);

    }

    entity.setDataCriacao(LocalDateTime.now());

  }

  public Iterable<Pagamento> getPagamentos() {
    Iterable<Pagamento> findAll = this.repository.findAll();
    return findAll;
  }

  public void getMetricasPagamento(Pagamento pagamento, final BigDecimal totalPedido) {

    BigDecimal vlrPago = new BigDecimal(BigDecimal.ZERO.toString());
    BigDecimal vlrTotalMultas = new BigDecimal(BigDecimal.ZERO.toString());
    BigDecimal vlrMultaAvaria = pagamento.getVlrMultaAvaria();
    BigDecimal vlrMultaExtravio = pagamento.getVlrMultaExtravio();

    if (null != vlrMultaExtravio) {
      vlrTotalMultas = vlrMultaAvaria.add(vlrMultaExtravio);
    }
    pagamento.setVlrTotalMultas(vlrTotalMultas);

    vlrPago = vlrTotalMultas.add(totalPedido);
    pagamento.setVlrPago(vlrPago);
    this.setDatasPagamento(pagamento);

  }

  private void setDatasPagamento(Pagamento pagamento) {

    LocalDateTime vencimentoPagamento = pagamento.getDataVencimento();
    if (vencimentoPagamento != null) {
      String data = UtilsData.getLocalDateTimeToString(vencimentoPagamento, "US", "dd/mm/yyyy");
      pagamento.setDtaVencimento(data);
    }
  }

  public void efetuaPagamento(Pagamento pagamento) {
    pagamento.setStatus(StatusPagamento.PAGO.name());
    pagamento.setDataPagamento(LocalDateTime.now());
    pagamento.setEfetuouPagamento(Boolean.TRUE);
    this.repository.saveAndFlush(pagamento);
  }

}
