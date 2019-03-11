package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.repository.ConfrontoRepository;

@Component
public class ConfrontoValidate {

    private final ConfrontoRepository repository;

    @Autowired
    public ConfrontoValidate(ConfrontoRepository repository) {
        this.repository = repository;
    }

    public void validaConfrontoExistente(final Jogo jogo) {
//        final Jogo user = this.repository.findByLogin(jogo.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
