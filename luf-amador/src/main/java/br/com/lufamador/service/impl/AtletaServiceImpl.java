package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Atleta;
import br.com.lufamador.model.AtletaHistory;
import br.com.lufamador.repository.AtletaRepository;
import br.com.lufamador.service.AgremiacaoService;
import br.com.lufamador.service.AtletaService;
import br.com.lufamador.validate.AtletaValidate;

@Service
public class AtletaServiceImpl implements AtletaService {

    @Autowired
    private AtletaRepository repository;
    @Autowired
    private AtletaValidate validate;
    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private AgremiacaoService agremiacaoService;
    @Autowired
    private AtletaHistoryServiceImpl atletaHistoryService;

    private Atleta cadastraAtleta(Atleta atleta) {
        Atleta atletaSaved = null;
        this.validate.validaAtletaExistente(atleta);
        try {
            if (atleta.getEndereco() != null)
                this.enderecoService.cadastraEndereco(atleta.getEndereco());

            atleta.setDataAfiliacao(LocalDate.now());
            atleta.setSuspenso(Boolean.FALSE);
            atletaSaved = this.repository.saveAndFlush(atleta);
        } catch (Exception e) {

        }

        LocalDate agora = LocalDate.now();
        String temporada = String.valueOf(agora.getYear());
        this.atletaHistoryService.insereNovoRegistroNoHistorico(atleta,
                atleta.getAgremiacao(), temporada);

        return atletaSaved;
    }

    private Atleta atulizarAtleta(final Atleta atletaAtualizar) {
        Atleta atletaAtualizado = null;
//        final Optional<Atleta> atleta = this.repository.findById(atletaAtualizar.getCodigo());
//        if (atleta.isPresent()) {
//            if (null != atletaAtualizar.getEndereco())
//                this.enderecoService.atualizaEndereco(atletaAtualizar.getEndereco());
//            if (null != atletaAtualizar.getAgremiacao())
//                this.agremiacaoService.createOrUpdate(atletaAtualizar.getAgremiacao());
//            atletaAtualizado = this.repository.saveAndFlush(atletaAtualizar);
//        }
//        return atletaAtualizado;
        return null;
    }

    public final Atleta suspenderAtleta(final Long codigo) {
//        Atleta atletaSuspenso = null;
//        final Optional<Atleta> atleta = this.repository.findById(codigo);
//        if (atleta.isPresent()) {
//            atleta.get()
//                    .setSuspenso(Boolean.TRUE);
//            atletaSuspenso = this.repository.saveAndFlush(atleta.get());
//        }
//        return atletaSuspenso;
        return null;
    }

    public final Atleta baixarSuspensaoAtleta(final Long codigo) {
//        Atleta atletaSuspenso = null;
//        final Optional<Atleta> atleta = this.repository.findById(codigo);
//        if (atleta.isPresent()) {
//            atleta.get()
//                    .setSuspenso(Boolean.FALSE);
//            atletaSuspenso = this.repository.saveAndFlush(atleta.get());
//    }
        return null;
    }

    private void excluirAtleta(final Long codigo) {
//        final Optional<Atleta> atleta = this.repository.findById(codigo);
//        if (atleta.isPresent())
//            this.repository.delete(atleta.get());

    }

    @Override
    public Page<Atleta> findAll(int page, int count) {
//        Pageable pages = PageRequest.of(page, count);
//        return this.repository.findAll(pages);
        return null;
    }

    @Override
    public Atleta createOrUpdate(Atleta atleta) {
        if (atleta.getCodigo() != null)
            return this.atulizarAtleta(atleta);
        return this.cadastraAtleta(atleta);
    }

    @Override
    public Atleta findByCodigo(Long codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public void delete(Long id) {
        this.excluirAtleta(id);
    }

    @Override
    public List<AtletaHistory> history(Long id) {
        List<AtletaHistory> list = this.atletaHistoryService.history(this.findByCodigo(id));
        list.sort(Comparator.comparing(AtletaHistory::getTemporada).reversed());
        return list;
    }

}
