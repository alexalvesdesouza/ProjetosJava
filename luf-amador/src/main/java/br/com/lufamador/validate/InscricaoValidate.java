package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Inscricao;
import br.com.lufamador.repository.InscricaoRepository;

@Component
public class InscricaoValidate {

    private final InscricaoRepository repository;

    @Autowired
    public InscricaoValidate(InscricaoRepository repository) {
        this.repository = repository;
    }

    public void validaInscricaoExistente(final Inscricao inscricao) {
//        final Inscricao user = this.repository.findByLogin(inscricao.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
