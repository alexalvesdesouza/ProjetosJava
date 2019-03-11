package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.repository.TabelaJogosRepository;

@Component
public class TabelaJogosValidate {

    private final TabelaJogosRepository repository;

    @Autowired
    public TabelaJogosValidate(TabelaJogosRepository repository) {
        this.repository = repository;
    }

    public void validaTabelaJogosExistente(final TabelaJogos campeonato) {
//        final TabelaJogos user = this.repository.findByLogin(campeonato.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
