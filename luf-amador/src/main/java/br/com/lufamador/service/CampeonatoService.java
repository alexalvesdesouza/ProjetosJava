package br.com.lufamador.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.model.Campeonato;
import br.com.lufamador.repository.CampeonatoRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;
import br.com.lufamador.validate.CampeonatoValidate;

@Service
public class CampeonatoService {

    private final CampeonatoRepository repository;
    private final CampeonatoValidate validate;
    private final InscricaoService inscricaoService;

    @Autowired
    public CampeonatoService(CampeonatoRepository repository, CampeonatoValidate validate,
            InscricaoService inscricaoService) {
        this.repository = repository;
        this.validate = validate;
        this.inscricaoService = inscricaoService;
    }

    public Campeonato cadastraCampeonato(Campeonato campeonato) {
        Campeonato campeonatoSaved = null;
        this.validate.validaCampeonatoExistente(campeonato);
        try {
            campeonato.setEdicao(String.valueOf(LocalDateTime.now().getYear()));
            campeonato.setInscricoesEncerradas(false);
            campeonato.setCampeonatoEncerrado(false);
            campeonatoSaved = this.repository.saveAndFlush(campeonato);
        } catch (Exception e) {

        }
        return campeonatoSaved;
    }

    public List<Campeonato> getCampeonatos(Boolean inscricoesAbertas) {

        if (!inscricoesAbertas)
            return this.repository.findAll();

        return this.repository.findAll().stream().filter(
                campeonato -> !campeonato.getInscricoesEncerradas()).collect(Collectors.toList());

    }

    public Campeonato findCampeonato(final Long codigo) {
        Optional<Campeonato> campeonato = this.repository.findById(codigo);
        return campeonato.get();

    }

    public final Campeonato atualizarCampeonato(Campeonato campeonato) {
        return this.repository.saveAndFlush(campeonato);
    }

    public final Campeonato inscricaoAgremiacaoCampeonato(Campeonato campeonato) {

        Campeonato campeonatoSaved = this.getCampeonato(campeonato.getCodigo());

//        if (inscricaoService.deletarInscricoes(campeonatoSaved.getInscricoes()))
        campeonato.getInscricoes().forEach(inscricaoService::cadastraInscricao);

        return this.atualizarCampeonato(campeonato);
    }

    private Campeonato getCampeonato(Long codigoCampeonato) {

        Optional<Campeonato> campeonato = this.repository.findById(codigoCampeonato);
        if (!campeonato.isPresent())
            throw new BussinessException(MensagensErro.CAMPEONATO_NAO_ENCONTRADO.replace("?", ""));
        return campeonato.get();
    }

}
