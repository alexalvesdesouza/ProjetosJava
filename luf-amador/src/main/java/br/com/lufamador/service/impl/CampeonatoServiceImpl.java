package br.com.lufamador.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Campeonato;
import br.com.lufamador.model.TabelaJogos;
import br.com.lufamador.repository.CampeonatoRepository;
import br.com.lufamador.service.AgremiacaoService;
import br.com.lufamador.service.CampeonatoService;
import br.com.lufamador.utils.mensagens.MensagensErro;
import br.com.lufamador.validate.CampeonatoValidate;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

    private final CampeonatoRepository repository;
    private final CampeonatoValidate validate;
    private final TabelaJogosService tabelaJogosService;
    private final AgremiacaoService agremiacaoService;

    @Autowired
    public CampeonatoServiceImpl(CampeonatoRepository repository, CampeonatoValidate validate,
            TabelaJogosService tabelaJogosService, AgremiacaoService agremiacaoService) {
        this.repository = repository;
        this.validate = validate;
        this.tabelaJogosService = tabelaJogosService;
        this.agremiacaoService = agremiacaoService;
    }

    private Campeonato create(Campeonato campeonato) {
        Campeonato campeonatoSaved = null;
        this.validate.validaCampeonatoExistente(campeonato);
        try {
            campeonato.setEdicao(String.valueOf(LocalDateTime.now().getYear()));
            campeonato.setInscricoesEncerradas(false);
            campeonato.setCampeonatoEncerrado(false);
            campeonatoSaved = this.repository.saveAndFlush(campeonato);
        } catch (Exception e) {
            throw new BussinessException(e.getMessage());
        }
        return campeonatoSaved;
    }

    public List<Campeonato> getCampeonatos() {
        List<Campeonato> list = this.repository.findAll()
                .stream()
                .filter(campeonato -> !campeonato.getInscricoesEncerradas())
                .collect(Collectors.toList());

        list.forEach(cam -> {
                    if (null != cam.getTabelaJogos()
                            && (null != cam.getTabelaJogos().getJogos() && !cam.getTabelaJogos().getJogos().isEmpty())) {
                        cam.getTabelaJogos().getJogos().forEach(jogo -> {
                            jogo.setDataAtualizacao(null);
                            jogo.setDataCriacao(null);
                        });
                    }
                }
        );

        list.forEach(campeonato ->
                campeonato.getInscricoes().sort(Comparator.comparing(Agremiacao::getNome)));

        return list;
    }

    public List<Campeonato> getCampeonatosAndamento() {
        List<Campeonato> list = this.repository.findAll()
                .stream()
                .filter(campeonato -> !campeonato.getCampeonatoEncerrado())
                .sorted(Comparator.comparing(Campeonato::getNomeCampeonato))
                .collect(Collectors.toList());

        list.forEach(campeonato -> {
                    if (null != campeonato.getTabelaJogos()
                            && (null != campeonato.getTabelaJogos().getJogos() && !campeonato.getTabelaJogos().getJogos().isEmpty())) {
                        campeonato.getTabelaJogos().getJogos().forEach(jogo -> {
                            jogo.setDataAtualizacao(null);
                            jogo.setDataCriacao(null);
                        });
                    }
                }
        );

        list.forEach(campeonato -> {
                    List<Agremiacao> agremiacoes = new ArrayList<>();
                    List<Agremiacao> agremiacoesEmConfronto = this.agremiacaoService.getAgremiacoesEmConfronto(
                            campeonato.getCodigo());
                    campeonato.getInscricoes().forEach(agremiacao -> {
                        if (!agremiacoesEmConfronto.contains(agremiacao)) {
                            agremiacoes.add(agremiacao);
                        }
                    });
                    campeonato.setInscricoes(agremiacoes);
                    campeonato.getInscricoes().sort(Comparator.comparing(Agremiacao::getNome));
                }
        );

        return list;
    }

    private Campeonato update(Campeonato campeonato) {
        TabelaJogos tabela = campeonato.getTabelaJogos();
        if (null != tabela)
            this.tabelaJogosService.createOrUdate(tabela);
        return this.repository.saveAndFlush(campeonato);
    }

    public final Campeonato inscricaoAgremiacaoCampeonato(Campeonato campeonato) {
        return this.update(campeonato);
    }

    private Campeonato getCampeonato(Long codigoCampeonato) {

        Optional<Campeonato> campeonato = this.repository.findById(codigoCampeonato);
        if (!campeonato.isPresent())
            throw new BussinessException(MensagensErro.CAMPEONATO_NAO_ENCONTRADO.replace("?", ""));
        return campeonato.get();
    }

    @Override
    public Page<Campeonato> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count, Sort.Direction.ASC, "nomeCampeonato");
        return this.repository.findAll(pages);
    }


    @Override
    public Campeonato createOrUpdate(Campeonato entity) {
        this.insereCorCampeonato(entity);
        if (null != entity.getCodigo())
            return this.update(entity);
        return this.create(entity);
    }

    public final Campeonato registraTabelaJotos(Campeonato entity) {
        Campeonato byCodigo = this.findByCodigo(entity.getCodigo());
        entity.setInscricoes(byCodigo.getInscricoes());
        return this.createOrUpdate(entity);
    }

    @Override
    public Campeonato findByCodigo(Long codigo) {
        return this.getCampeonato(codigo);
    }

    @Override
    public void delete(Long codigo) {
        try {

            this.repository.delete(this.findByCodigo(codigo));
        } catch (Exception e) {
            throw new BussinessException("Campeonato esta em andamento e não pode mais ser excluido.");
        }
    }

    private void insereCorCampeonato(Campeonato campeonato) {

        String categoria = campeonato.getCategoria();
        String cor = null;
        switch (categoria) {
            case "AMADOR_ESPECIAL":
                cor = "blue lighten-1";
                break;
            case "AMADOR_ACESSO":
                cor = "lime lighten-1";
                break;
            case "VETERANOS":
                cor = "deep-purple lighten-1";
                break;
            case "RURAL":
                cor = "deep-orange lighten-1";
                break;
            case "JUNIORES":
                cor = "light-green lighten-1";
                break;
            case "JUVENIL":
                cor = "yellow lighten-1";
                break;

            default:
                cor = "red";
                break;
        }

        campeonato.setCor(cor);

    }
}
