package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exception.ValidateException;
import br.com.lufamador.model.Tjdu;
import br.com.lufamador.repository.TjduRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;

@Component
public class TjduValidate {

    private final TjduRepository repository;

    @Autowired
    public TjduValidate(TjduRepository repository) {
        this.repository = repository;
    }

    public void validaCadastroTJDU(final Tjdu tjdu) {
        this.validaTjduExistente(tjdu);
    }

    public void validaTjduExistente(final Tjdu tjdu) {
        final Tjdu tjduValidate = this.repository.findByNumeroAndCategoria(tjdu.getNumero(), tjdu.getCategoria());
        if (tjduValidate != null)
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?",
                    tjduValidate.getCategoria().concat(" - ").concat(tjduValidate.getNumero())
                            .concat
                                    (" - Tjdu")));

    }
}
