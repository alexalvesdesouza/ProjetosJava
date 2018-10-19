package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Intervencao;
import br.com.lufamador.repository.IntervencaoRepository;

@Service
public class IntervencaoServiceImpl {

    @Autowired
    private IntervencaoRepository repository;

    public List<Intervencao> createOrUpdate(List<Intervencao> intervencoes, int pontosCorrigidos, int pontosAntigos) {
        intervencoes.forEach(intervencao -> {

            if (null == intervencao.getCodigo()) {
                int pontosGanhos = 0;
                int pontosPerdidos = 0;

                if (pontosCorrigidos > pontosAntigos) {
                    pontosGanhos = pontosCorrigidos - pontosAntigos;
                }

                if (pontosAntigos > pontosCorrigidos) {
                    pontosPerdidos = pontosAntigos - pontosCorrigidos;
                }

                intervencao.setPontosGanhos(pontosGanhos);
                intervencao.setPontosPerdidos(pontosPerdidos);
                this.repository.save(intervencao);

            }
        });
        return intervencoes;
    }
}
