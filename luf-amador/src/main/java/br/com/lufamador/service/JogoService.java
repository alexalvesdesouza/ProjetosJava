package br.com.lufamador.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.repository.JogoRepository;
import br.com.lufamador.validate.JogoValidate;

@Service
public class JogoService {

    private final JogoRepository repository;
    private final JogoValidate validate;
    private final ClassificacaoService classificacaoService;

    @Autowired
    public JogoService(JogoRepository repository, JogoValidate validate, ClassificacaoService classificacaoService) {
        this.repository = repository;
        this.validate = validate;
        this.classificacaoService = classificacaoService;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo cadastraJogo(Jogo jogo) {
        jogo.setDataAtualizacao(LocalDateTime.now());
        jogo.setDataCriacao(LocalDateTime.now());
        Jogo jogoSaved = null;
        this.validate.validaJogoExistente(jogo);
        try {
            jogoSaved = this.repository.saveAndFlush(jogo);
        } catch (Exception e) {
        }
        return jogoSaved;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo atualizarJogo(final Jogo jogo) {
        jogo.setDataAtualizacao(LocalDateTime.now());
        final Jogo jogoAtualizado = this.repository.saveAndFlush(jogo);
        this.classificacaoService.geraClassificacao(jogoAtualizado);
        return jogoAtualizado;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void cadastrarJogos(final List<Jogo> jogos) {
        jogos.forEach(jogo -> this.cadastraJogo(jogo));
    }

    public List<Jogo> getJogosTempoReal() {
//        return this.repository.getJogosParaTempoReal(LocalDate.now());
        return this.repository.getJogosParaTempoReal(LocalDate.of(2018, 05, 06));
    }
}
