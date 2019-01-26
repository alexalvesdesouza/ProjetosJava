package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Endereco;
import br.com.lufamador.repository.EnderecoRepository;

@Component
public class EnderecoValidate {

    private final EnderecoRepository repository;

    @Autowired
    public EnderecoValidate(EnderecoRepository repository) {
        this.repository = repository;
    }

    public void validaEnderecoExistente(final Endereco endereco) {
//        final Endereco user = this.repository.findByLogin(endereco.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
