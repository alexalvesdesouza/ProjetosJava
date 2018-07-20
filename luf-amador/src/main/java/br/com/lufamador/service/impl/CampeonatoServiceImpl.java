package br.com.lufamador.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.model.Campeonato;
import br.com.lufamador.model.Inscricao;
import br.com.lufamador.repository.CampeonatoRepository;
import br.com.lufamador.service.CampeonatoService;
import br.com.lufamador.utils.mensagens.MensagensErro;
import br.com.lufamador.validate.CampeonatoValidate;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

    private final CampeonatoRepository repository;
    private final CampeonatoValidate validate;
    private final InscricaoService inscricaoService;

    @Autowired
    public CampeonatoServiceImpl(CampeonatoRepository repository, CampeonatoValidate validate,
            InscricaoService inscricaoService) {
        this.repository = repository;
        this.validate = validate;
        this.inscricaoService = inscricaoService;
    }

    private Campeonato create(Campeonato campeonato) {
        Campeonato campeonatoSaved = null;
        this.validate.validaCampeonatoExistente(campeonato);
        try {
            campeonato.setEdicao(String.valueOf(LocalDateTime.now()
                    .getYear()));
            campeonato.setInscricoesEncerradas(false);
            campeonato.setCampeonatoEncerrado(false);
            campeonatoSaved = this.repository.saveAndFlush(campeonato);
        } catch (Exception e) {

        }
        return campeonatoSaved;
    }

    public List<Campeonato> getCampeonatos() {
        return this.repository.findAll()
                .stream()
                .filter(campeonato -> !campeonato.getInscricoesEncerradas())
                .collect(Collectors.toList());
    }

    private Campeonato findCampeonato(final Long codigo) {
        Optional<Campeonato> campeonato = this.repository.findById(codigo);
        return campeonato.get();

    }

    private Campeonato update(Campeonato campeonato) {
        return this.repository.saveAndFlush(campeonato);
    }

    public final Campeonato inscricaoAgremiacaoCampeonato(Campeonato campeonato) {

        Campeonato campeonatoSaved = this.getCampeonato(campeonato.getCodigo());
//        deletarInscricoes(campeonatoSaved.getInscricoes());
//        List<Inscricao> incricoes = new ArrayList<>();
//        campeonato.getInscricoes()
//                .forEach(inscricao -> {
//                    Inscricao cadastraInscricao = inscricaoService.cadastraInscricao(inscricao);
//                    incricoes.add(cadastraInscricao);
//                });
//
//        campeonato.setInscricoes(new ArrayList<>());
//        campeonato.setInscricoes(incricoes);

        return this.update(campeonato);
    }

    private void deletarInscricoes(List<Inscricao> inscricoes) {
        try {

            inscricoes.stream()
                    .forEach(inscricao -> {
                        this.repository.deleteFromLufCampeonatoInscricoes(inscricao.getCodigo());
                        this.inscricaoService.deletaInscricao(inscricao);
                    });

        } catch (Exception e) {
            throw new BussinessException("Erro ao excluir inscricoes");
        }
    }

    private Campeonato getCampeonato(Long codigoCampeonato) {

        Optional<Campeonato> campeonato = this.repository.findById(codigoCampeonato);
        if (!campeonato.isPresent())
            throw new BussinessException(MensagensErro.CAMPEONATO_NAO_ENCONTRADO.replace("?", ""));
        return campeonato.get();
    }

    @Override
    public Page<Campeonato> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        return this.repository.findAll(pages);
    }


    @Override
    public Campeonato createOrUpdate(Campeonato entity) {
        if (null != entity.getCodigo())
            return this.update(entity);
        return this.create(entity);
    }

    @Override
    public Campeonato findByCodigo(Long codigo) {
        return this.getCampeonato(codigo);
    }

    @Override
    public void delete(Long codigo) {
        this.delete(codigo);
    }
}
