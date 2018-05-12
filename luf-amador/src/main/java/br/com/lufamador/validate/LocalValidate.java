package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exception.ValidateException;
import br.com.lufamador.model.Local;
import br.com.lufamador.repository.LocalRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;

@Component
public class LocalValidate {

    private final LocalRepository repository;

    @Autowired
    public LocalValidate(LocalRepository repository) {
        this.repository = repository;
    }

    public void validaLocalExistente(final Local local) {
        final Local local1 = this.repository.findByNome(local.getNome());
        if (local1 != null)
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "Local"));

    }
}
