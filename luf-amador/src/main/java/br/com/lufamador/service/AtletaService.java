package br.com.lufamador.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Atleta;
import br.com.lufamador.repository.AtletaRepository;
import br.com.lufamador.validate.AtletaValidate;

@Service
public class AtletaService {

    private final AtletaRepository repository;
    private final AtletaValidate validate;
    private final EnderecoService enderecoService;

    @Autowired
    public AtletaService(AtletaRepository repository, AtletaValidate validate, EnderecoService enderecoService) {
        this.repository = repository;
        this.validate = validate;
        this.enderecoService = enderecoService;
    }

    public Atleta cadastraAtleta(Atleta atleta) {
        Atleta atletaSaved = null;
        this.validate.validaAtletaExistente(atleta);
        try {
            this.enderecoService.cadastraEndereco(atleta.getEndereco());
            atleta.setDataAfiliacao(LocalDate.now());
            atleta.setSuspenso(Boolean.FALSE);
            atletaSaved = this.repository.saveAndFlush(atleta);
        } catch (Exception e) {

        }
        return atletaSaved;
    }

    public final Atleta atulizarAtleta(final Atleta atletaAtualizar) {
        Atleta atletaAtualizado = null;
        final Optional<Atleta> atleta = this.repository.findById(atletaAtualizar.getCodigo());
        if (atleta.isPresent()) {
            atletaAtualizado = this.repository.saveAndFlush(atleta.get());
        }
        return atletaAtualizado;
    }

    public final Atleta suspenderAtleta(final Long codigo) {
        Atleta atletaSuspenso = null;
        final Optional<Atleta> atleta = this.repository.findById(codigo);
        if (atleta.isPresent()) {
            atleta.get().setSuspenso(Boolean.TRUE);
            atletaSuspenso = this.repository.saveAndFlush(atleta.get());
        }
        return atletaSuspenso;
    }

    public final Atleta baixarSuspensaoAtleta(final Long codigo) {
        Atleta atletaSuspenso = null;
        final Optional<Atleta> atleta = this.repository.findById(codigo);
        if (atleta.isPresent()) {
            atleta.get().setSuspenso(Boolean.FALSE);
            atletaSuspenso = this.repository.saveAndFlush(atleta.get());
        }
        return atletaSuspenso;
    }

    public void excluirAtleta(final Long codigo) {
        final Optional<Atleta> atleta = this.repository.findById(codigo);
        if (atleta.isPresent())
            this.repository.delete(atleta.get());

    }

    public List<Atleta> getAtletas() {
        return this.repository.findAll();
    }
}
