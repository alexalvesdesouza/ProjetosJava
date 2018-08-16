package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.repository.AgremiacaoRepository;
import br.com.lufamador.service.AgremiacaoService;
import br.com.lufamador.utils.mensagens.MensagensErro;
import br.com.lufamador.validate.AgremiacaoValidate;

@Service
public class AgremiacaoServiceImpl implements AgremiacaoService {

    private final AgremiacaoRepository repository;
    private final AgremiacaoValidate validate;
    private final EnderecoService enderecoService;

    @Autowired
    public AgremiacaoServiceImpl(AgremiacaoRepository repository, AgremiacaoValidate validate,
            EnderecoService enderecoService) {
        this.repository = repository;
        this.validate = validate;
        this.enderecoService = enderecoService;
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

    public final String getNomeSigla(String sigla) {

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

    public List<Agremiacao> getAgremiacoesDisponiveis(final Long codigoCampeonato, final String categoria) {
        return this.repository.getAgremiacoesDisponiveis(codigoCampeonato, categoria)
                .stream()
                .filter(agremiacao -> !agremiacao.getInativa())
                .collect(Collectors.toList());
    }

    private final Agremiacao atualizaAgremiacao(Agremiacao agremiacao) {
        this.validate.validaAtualizacaoAgremiacao(agremiacao);
        this.calculaDataMandatoDiretoria(agremiacao);
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
        Pageable pages = PageRequest.of(page, count);
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
