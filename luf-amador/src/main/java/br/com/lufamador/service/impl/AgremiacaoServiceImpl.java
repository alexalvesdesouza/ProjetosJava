package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.repository.AgremiacaoRepository;
import br.com.lufamador.service.AgremiacaoService;
import br.com.lufamador.utils.mensagens.MensagensErro;
import br.com.lufamador.validate.AgremiacaoValidate;

@Service
public class AgremiacaoServiceImpl implements AgremiacaoService {

    private final AgremiacaoRepository repository;
    private final AgremiacaoValidate validate;

    @Autowired
    public AgremiacaoServiceImpl(AgremiacaoRepository repository, AgremiacaoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    private Agremiacao cadastraAgremiacao(Agremiacao agremiacao) {
        Agremiacao agremiacaoSaved = null;
        agremiacao.setNomeSigla(this.getNomeSigla(agremiacao.getNome()));

        this.validate.validaCadastroAgremiacao(agremiacao);
        try {
            this.calculaDataMandatoDiretoria(agremiacao);
            if (null == agremiacao.getInativa())
                agremiacao.setInativa(false);
            agremiacaoSaved = this.repository.saveAndFlush(agremiacao);
        } catch (Exception e) {
        }
        return agremiacaoSaved;
    }

    private void calculaDataMandatoDiretoria(Agremiacao agremiacao) {
        final LocalDate dataAfiliacao = agremiacao.getDataAfiliacao();
        final LocalDate dataAtual = LocalDate.now();
        LocalDate dataMandatoDiretoria = dataAfiliacao.plusYears(4l);

        boolean finalizouCalculo = false;

        while (!finalizouCalculo) {

            if (dataMandatoDiretoria.isAfter(dataAtual)) {
                finalizouCalculo = true;
            } else {
                dataMandatoDiretoria = dataMandatoDiretoria.plusYears(4l);
            }
        }
        agremiacao.setDataMandatoDiretoria(dataMandatoDiretoria);
    }

    private String getNomeSigla(String sigla) {

        String siglaRetorno = sigla.toLowerCase();
        siglaRetorno = siglaRetorno.replace(" ", "")
                .replace("ç", "c")
                .replace("á", "a")
                .replace("à", "a")
                .replace("â", "a")
                .replace("ã", "a")
                .replace("é", "e")
                .replace("è", "e")
                .replace("ê", "e")
                .replace("ẽ", "e")
                .replace("í", "i")
                .replace("ì", "i")
                .replace("î", "i")
                .replace("ĩ", "i")
                .replace("ó", "o")
                .replace("ò", "o")
                .replace("ô", "o")
                .replace("õ", "o")
                .replace("ú", "u")
                .replace("ù", "u")
                .replace("û", "u")
                .replace("ũ", "u")
                .replace(".", "")
                .replace("_", "");

        return siglaRetorno.toUpperCase();
    }

    public List<Agremiacao> getAgremiacoesInscritas(final Long codigoCampeonato) {
        return this.repository.getAgremiacoesInscritas(codigoCampeonato);
    }

    @Override
    public List<Agremiacao> loadAll() {
        List<Agremiacao> all = this.repository.findAll();
        all.sort(Comparator.comparing(Agremiacao::getNome));
        return all;
    }

    public List<Agremiacao> getAgremiacoesDisponiveis(final Long codigoCampeonato, final String categoria) {
        return this.repository.getAgremiacoesDisponiveis(codigoCampeonato, categoria)
                .stream()
                .filter(agremiacao -> !agremiacao.getInativa())
                .sorted()
                .collect(Collectors.toList());
    }

    private final Agremiacao atualizaAgremiacao(Agremiacao agremiacao) {
        this.validate.validaAtualizacaoAgremiacao(agremiacao);
        this.calculaDataMandatoDiretoria(agremiacao);
        agremiacao.setNomeSigla(this.getNomeSigla(agremiacao.getNome()));
        return this.repository.saveAndFlush(agremiacao);
    }

    public void deletarAgremiacao(final Long codigo) {
        final Agremiacao agremiacao = this.getAgremiacao(codigo);
        this.validate.deletarAgremiacao(agremiacao);
        this.repository.delete(agremiacao);
    }

    public Agremiacao getAgremiacao(final Long codigo) {
        Optional<Agremiacao> agremiacao = this.repository.findById(codigo);
        if (!agremiacao.isPresent())
            throw new BussinessException(MensagensErro.ENTIDADE_INEXISTENTE);
        return agremiacao.get();
    }

    @Override
    public Page<Agremiacao> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count, Sort.Direction.ASC, "nome");
        return this.repository.findAll(pages);
    }

    public Map<String, Agremiacao> findAllMap(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        Page<Agremiacao> all = this.repository.findAll(pages);

        Map<String, Agremiacao> map = new HashMap<>();
        all.getContent().forEach(agremiacao -> {
            map.put(agremiacao.getNome(), agremiacao);
        });

        return map;
    }

    @Override
    public List<Agremiacao> getAgremiacoesEmConfronto(Long codigoCampeonato) {
        return this.repository.getAgremiacoesEmJogo(codigoCampeonato);
    }

    @Override
    public List<Agremiacao> findAllClassificadas(Long codigo, String fase) {

        boolean isClassificada = true;

        if (fase.equalsIgnoreCase("1")) {
            return this.repository.getAgremiacoesInscritasFilter(codigo);
        }

        if (fase.equalsIgnoreCase("2")) {
            fase = "1";
        } else if (fase.equalsIgnoreCase("QUARTAS")) {
            fase = "2";
        } else if (fase.equalsIgnoreCase("SEMI-FINAL")) {
            fase = "QUARTAS";
        } else if (fase.equalsIgnoreCase("FINAL")) {
            fase = "SEMI-FINAL";
        } else {
            fase = "1";
            isClassificada = false;
        }

        return this.repository.getAgremiacoesClassificadas(codigo, fase, isClassificada).stream().sorted(
                Comparator.comparing(Agremiacao::getNome)).collect(Collectors.toList());
    }

    @Override
    public Agremiacao createOrUpdate(Agremiacao agremiacao) {
        if (agremiacao.getCodigo() != null)
            return this.atualizaAgremiacao(agremiacao);
        return this.cadastraAgremiacao(agremiacao);
    }

    @Override
    public Agremiacao findByCodigo(Long codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public void delete(Long id) {
        this.deletarAgremiacao(id);
    }
}
