package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exceptions.ValidateException;
import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.repository.DepartamentoTecnicoRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;

@Component
public class DepartamentoTecnicoValidate {

    private final DepartamentoTecnicoRepository repository;

    @Autowired
    public DepartamentoTecnicoValidate(DepartamentoTecnicoRepository repository) {
        this.repository = repository;
    }

    public void validaCadastroTJDU(final DepartamentoTecnico departamentoTecnico) {
        this.validaDepartamentoTecnicoExistente(departamentoTecnico);
    }

    public void validaDepartamentoTecnicoExistente(final DepartamentoTecnico departamentoTecnico) {
        final DepartamentoTecnico departamentoTecnicoValidate = this.repository.findByNumeroAndCategoria(
                departamentoTecnico.getNumero(), departamentoTecnico.getCategoria());
        if (departamentoTecnicoValidate != null)
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?",
                    departamentoTecnicoValidate.getCategoria().concat(" - ").concat(
                            departamentoTecnicoValidate.getNumero())
                            .concat
                                    (" - DepartamentoTecnico")));

    }
}
