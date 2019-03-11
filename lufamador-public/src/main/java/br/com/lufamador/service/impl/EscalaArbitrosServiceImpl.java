package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.EscalaArbitros;
import br.com.lufamador.repository.EscalaArbitrosRepository;

@Service
public class EscalaArbitrosServiceImpl {

    @Autowired
    private EscalaArbitrosRepository repository;

    public List<EscalaArbitros> getEscalaArbitrosList(String temporada) {
        
        if (null == temporada || "".equals(temporada)) {
            temporada = String.valueOf(LocalDate.now().getYear());
        }
        return this.filtroPorTemporada(temporada);
    }

    private List<EscalaArbitros> filtroPorTemporada(String temporada) {
        return this.repository.findAll().stream().filter(escala -> escala.getTemporada().equals(temporada))
                .collect(Collectors.toList());
    }

}
