package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.repository.JogoRepository;

@Service
public class JogoService {

    @Autowired
    private JogoRepository repository;

    public List<Jogo> jogosTempoRealPorCategoria(String categoria) {
        List<Jogo> jogos = this.getJogosTempoReal()
                .stream()
                .filter(jogo -> jogo.getAgremiacaoA().getCategoria().equals(categoria)
                        || jogo.getAgremiacaoB().getCategoria().equals(categoria))
                .collect(
                        Collectors.toList());

        jogos.stream().sorted(Comparator.comparing(Jogo::getDataAtualizacao).reversed()).collect(
                Collectors.toList());

        jogos.forEach(item -> {
            item.setDataCriacao(null);
            item.setDataAtualizacao(null);
        });

        return jogos;
    }

    private List<Jogo> getJogosTempoReal() {
        return this.repository.getJogosParaTempoReal(LocalDate.now())
                .stream()
                .filter(jogo -> !jogo.getPartidaEncerrada())
                .collect(Collectors.toList());

    }

    public List<Jogo> getResultadosJogos(String categoria) {
        List<Jogo> jogos = this.repository.findAll()
                .stream()
                .filter(jogo -> jogo.getPartidaEncerrada())
                .collect(Collectors.toList());

        return jogos.stream().filter(jogo -> jogo.getAgremiacaoA().getCategoria().equals(categoria)
                || jogo.getAgremiacaoB().getCategoria().equals(categoria)).collect(Collectors.toList());
    }
}
