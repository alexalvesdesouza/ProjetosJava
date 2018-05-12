package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Contato;
import br.com.lufamador.repository.ContatoRepository;

@Component
public class ContatoValidate {

    private final ContatoRepository repository;

    @Autowired
    public ContatoValidate(ContatoRepository repository) {
        this.repository = repository;
    }

    public void validaContatoExistente(final Contato contato) {
//        final Contato user = this.repository.findByLogin(contato.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
