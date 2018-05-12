package br.com.lufamador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Tjdu;
import br.com.lufamador.repository.TjduRepository;
import br.com.lufamador.validate.TjduValidate;

@Service
public class TjduService {

    private final TjduRepository repository;
    private final TjduValidate validate;

    @Autowired
    public TjduService(TjduRepository repository, TjduValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Tjdu cadastraTjdu(Tjdu tjdu) {
        Tjdu tjduSaved = null;
        tjdu.setCategoria(tjdu.getCategoria().toUpperCase());
        this.validate.validaCadastroTJDU(tjdu);
        try {
            tjduSaved = this.repository.saveAndFlush(tjdu);
        } catch (Exception e) {
        }
        return tjduSaved;
    }

    public Tjdu editarTjdu(Tjdu tjdu) {
        Tjdu tjduSaved = null;
        //this.validate.validaCadastroTJDU(tjdu);
        tjdu.setCategoria(tjdu.getCategoria().toUpperCase());
        try {
            tjduSaved = this.repository.saveAndFlush(tjdu);
        } catch (Exception e) {
        }
        return tjduSaved;
    }

    public List<Tjdu> getTjduList(final String categoria) {
        return this.repository.findByCategoria(categoria);
    }

    public void deletarTjdu(final Long codigo) {
        Optional<Tjdu> tjud = this.repository.findById(codigo);
        if (tjud.isPresent())
            this.repository.delete(tjud.get());
        //TODO criar classe de BussinessException
    }
}
