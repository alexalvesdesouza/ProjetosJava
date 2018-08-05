package br.com.lufamador.validate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.repository.JogoRepository;

@Component
public class JogoValidate {

    private final JogoRepository repository;

    @Autowired
    public JogoValidate(JogoRepository repository) {
        this.repository = repository;
    }

    public boolean validaJogoExistente(final Jogo jogo) {
        if (null != jogo.getCodigo()) {
            final Optional<Jogo> jogoSaved = this.repository.findById(jogo.getCodigo());
        }
        return false;
    }
}
