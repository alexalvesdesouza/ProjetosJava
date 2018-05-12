package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exception.ValidateException;
import br.com.lufamador.model.EscalaArbitros;
import br.com.lufamador.repository.EscalaArbitrosRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;

@Component
public class EscalaArbitrosValidate {

    private final EscalaArbitrosRepository repository;

    @Autowired
    public EscalaArbitrosValidate(EscalaArbitrosRepository repository) {
        this.repository = repository;
    }

    public void validaEscalaArbitrosExistente(final EscalaArbitros escalaArbitros) {
        final EscalaArbitros escalaArbitros1 = this.repository.findByNumero(escalaArbitros.getNumero());
        if (escalaArbitros1 != null)
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "EscalaArbitros"));

    }
}
