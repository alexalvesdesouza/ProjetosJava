package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.repository.ClassificacaoRepository;

@Component
public class ClassificacaoValidate {

    private final ClassificacaoRepository repository;

    @Autowired
    public ClassificacaoValidate(ClassificacaoRepository repository) {
        this.repository = repository;
    }

    public void validaClassificacaoExistente(final Classificacao classificacao) {
//        final Classificacao user = this.repository.findByLogin(classificacao.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
