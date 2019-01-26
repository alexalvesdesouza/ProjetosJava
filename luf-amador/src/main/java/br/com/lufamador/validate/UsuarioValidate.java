package br.com.lufamador.validate;

import static br.com.lufamador.constants.ErrorsCode.USUARIO_DUPLICADO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exceptions.BusinessException;
import br.com.lufamador.model.Usuario;
import br.com.lufamador.repository.UsuarioRepository;

@Component
public class UsuarioValidate {

    @Autowired
    private UsuarioRepository repository;

    public void validaInsercao(Usuario usuario) {
        this.validaUniqueLogin(usuario);
        this.validaSeExaminador(usuario);
    }

    private void validaUniqueLogin(Usuario usuario) {
        Optional<Usuario> user = this.repository.findByEmail(usuario.getEmail());
        if (user.isPresent()) {
            throw new BusinessException(USUARIO_DUPLICADO);
        }
    }

    private void validaSeExaminador(Usuario usuario) {

    }

}
