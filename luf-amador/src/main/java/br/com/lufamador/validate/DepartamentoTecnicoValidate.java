package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exception.ValidateException;
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

    public void validaDepartamentoTecnicoExistente(final DepartamentoTecnico departamentoTecnico) {
        final DepartamentoTecnico departamentoTecnico1 = this.repository.findByNumeroAndAndCategoria
                (departamentoTecnico.getNumero(), departamentoTecnico.getCategoria());
        if (departamentoTecnico1 != null)
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "Departamento Técnico"));

    }
}
