package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.EscalaArbitros;
import br.com.lufamador.repository.EscalaArbitrosRepository;
import br.com.lufamador.validate.EscalaArbitrosValidate;

@Service
public class EscalaArbitrosService {

    private final EscalaArbitrosRepository repository;
    private final EscalaArbitrosValidate validate;

    @Autowired
    public EscalaArbitrosService(EscalaArbitrosRepository repository, EscalaArbitrosValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public EscalaArbitros cadastraEscalaArbitros(EscalaArbitros escalaArbitros) {
        EscalaArbitros escalaArbitrosSaved = null;
        this.validate.validaEscalaArbitrosExistente(escalaArbitros);
        try {
            escalaArbitrosSaved = this.repository.saveAndFlush(escalaArbitros);
        } catch (Exception e) {
        }
        return escalaArbitrosSaved;
    }

    public EscalaArbitros atualizarEscalaArbitros(EscalaArbitros escalaArbitros) {

        EscalaArbitros escalaArbitrosSaved = this.repository.saveAndFlush(escalaArbitros);

        return escalaArbitrosSaved;
    }

    public List<EscalaArbitros> getEscalaArbitrosList() {
        return this.repository.findAll();
    }

    public void excluirEscala(final Long codigo) {
        EscalaArbitros byCodigo = this.repository.findByCodigo(codigo);
        this.repository.delete(byCodigo);
    }
}
