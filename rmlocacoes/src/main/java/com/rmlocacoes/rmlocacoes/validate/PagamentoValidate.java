package com.rmlocacoes.rmlocacoes.validate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.rmlocacoes.rmlocacoes.model.Pagamento;
import com.rmlocacoes.rmlocacoes.util.ValidateException;

@Component
public class PagamentoValidate {

  private final String MS_CONFLITO_DATA_ANTERIOR = "Data de ? não pode ser anterior à data Atual";

  public void validaPagamento(Pagamento pagamento) {

    this.validaVencimentoAntesAgora(pagamento);

  }
  
  private void validaVencimentoAntesAgora(Pagamento pagamento) {
    LocalDate agora = LocalDateTime.now()
                                   .toLocalDate();

    final LocalDateTime dataVencimento = pagamento.getDataVencimento();
    final LocalDateTime dataCancelamento = pagamento.getDataCancelamento();

    if (dataVencimento != null) {
      final LocalDate localDate = dataVencimento.toLocalDate();
      if (agora.isAfter(localDate))
        throw new ValidateException(MS_CONFLITO_DATA_ANTERIOR.replace("?", "Vencimento"));
    }

    if (dataCancelamento != null) {
      final LocalDate localDate = dataCancelamento.toLocalDate();
      if (agora.isAfter(localDate))
        throw new ValidateException(MS_CONFLITO_DATA_ANTERIOR.replace("?", "Cancelamento"));
    }

  }

}
