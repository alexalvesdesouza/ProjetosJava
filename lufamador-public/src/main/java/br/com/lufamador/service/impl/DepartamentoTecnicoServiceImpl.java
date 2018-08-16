package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.repository.DepartamentoTecnicoRepository;

@Service
public class DepartamentoTecnicoServiceImpl {

    @Autowired
    private DepartamentoTecnicoRepository repository;

    public List<DepartamentoTecnico> getDepartamentoTecnicoList(String categoria) {
        return this.repository.findByCategoria(categoria);
    }


}
