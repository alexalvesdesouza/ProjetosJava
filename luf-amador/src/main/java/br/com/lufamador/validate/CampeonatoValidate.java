package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Campeonato;
import br.com.lufamador.repository.CampeonatoRepository;

@Component
public class CampeonatoValidate {

    private final CampeonatoRepository repository;

    @Autowired
    public CampeonatoValidate(CampeonatoRepository repository) {
        this.repository = repository;
    }

    public void validaCampeonatoExistente(final Campeonato campeonato) {
//        final Campeonato user = this.repository.findByLogin(campeonato.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
