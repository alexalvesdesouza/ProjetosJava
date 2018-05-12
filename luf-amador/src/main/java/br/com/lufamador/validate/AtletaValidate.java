package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Atleta;
import br.com.lufamador.repository.AtletaRepository;

@Component
public class AtletaValidate {

    private final AtletaRepository repository;

    @Autowired
    public AtletaValidate(AtletaRepository repository) {
        this.repository = repository;
    }

    public void validaAtletaExistente(final Atleta atleta) {
//        final Atleta user = this.repository.findByLogin(atleta.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
