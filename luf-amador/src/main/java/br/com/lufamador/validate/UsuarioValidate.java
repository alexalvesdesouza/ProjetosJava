package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.utils.mensagens.MensagensErro;
import br.com.lufamador.exception.ValidateException;
import br.com.lufamador.model.Usuario;
import br.com.lufamador.repository.UsuarioRepository;

@Component
public class UsuarioValidate {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioValidate(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void validaUsuarioExistente(final Usuario usuario) {
        final Usuario user = this.repository.findByLogin(usuario.getLogin());
        if (user != null && user.getAtivo())
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "Usuário"));

    }
}
