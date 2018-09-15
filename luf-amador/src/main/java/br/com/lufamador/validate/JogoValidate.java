package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.repository.JogoRepository;

@Component
public class JogoValidate {

    private final JogoRepository repository;

    @Autowired
    public JogoValidate(JogoRepository repository) {
        this.repository = repository;
    }

    public boolean validaSeJogoEstaDuplicado(final String keyConfronto) {
        return this.repository.findByKeyConfronto(keyConfronto) != null;
    }
}
