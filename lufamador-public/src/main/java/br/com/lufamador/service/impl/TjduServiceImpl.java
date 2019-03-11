package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.MembroTjdu;
import br.com.lufamador.model.Tjdu;
import br.com.lufamador.repository.MembroTjduRepository;
import br.com.lufamador.repository.TjduRepository;

@Service
public class TjduServiceImpl {

    @Autowired
    private TjduRepository repository;

    @Autowired
    private MembroTjduRepository repositoryMembrosTjdu;

    public List<Tjdu> getTjduList(final String categoria, String temporada) {

        if (null == temporada || "".equals(temporada)) {
            temporada = String.valueOf(LocalDate.now().getYear());
        }
        return this.filtraPorTemporada(categoria, temporada);
    }

    private List<Tjdu> filtraPorTemporada(String categoria, String temporada) {
        return this.repository.findByCategoria(categoria).stream().filter(
                tjdu -> tjdu.getTemporada().equals(temporada)).collect(
                Collectors.toList());
    }

    public List<MembroTjdu> getMembros() {
        return this.repositoryMembrosTjdu.findAll();
    }

}
