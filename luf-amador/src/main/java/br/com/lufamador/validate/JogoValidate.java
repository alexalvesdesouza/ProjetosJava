package br.com.lufamador.validate;

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

    public void validaJogoExistente(final Jogo jogo) {
//        final Jogo jogo1 = this.repository.findByNome(jogo.getNome());
//        if (jogo1 != null)
//            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "Jogo"));

    }
}
