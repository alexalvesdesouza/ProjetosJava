package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                .sorted(Comparator.comparing(Jogo::getDataAtualizacao).reversed())
                .collect(
                        Collectors.toList());

        jogos.forEach(item -> {
            item.setDataCriacao(null);
            item.setDataAtualizacao(null);
        });

        return jogos;
    }

    private List<Jogo> getJogosTempoReal() {
        LocalDate data = LocalDate.now().plusDays(-1l);
        return this.repository.getJogosParaTempoReal(data, LocalDate.now());

    }

    public List<Jogo> getResultadosJogos(String categoria, String dataRodada) {
        LocalDate data;
        List<Jogo> jogos;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            data = LocalDate.parse(dataRodada, formatter);

            jogos = this.repository.getJogosParaTempoReal(data, data);
        } catch (Exception e) {
            data = LocalDate.now().plusDays(-6);
            jogos = this.repository.getJogosParaTempoReal(data, LocalDate.now());
        }

        if (null == jogos)
            return new ArrayList<>();

        return jogos.stream().filter(jogo -> jogo.getAgremiacaoA().getCategoria().equals(categoria)
                || jogo.getAgremiacaoB().getCategoria().equals(categoria))
                .filter(jg -> jg.getPartidaEncerrada())
                .collect(Collectors.toList());
    }

    public List<String> getDatasPartidas() {
        return this.repository.getDatasPartidas().stream().sorted().collect(Collectors.toList());
    }
}
