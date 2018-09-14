package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Arbitro;
import br.com.lufamador.repository.ArbitroRepository;

@Service
public class ArbitrosServiceImpl {

    @Autowired
    private ArbitroRepository repository;

    public List<Arbitro> getArbitros() {
        return this.repository.findAll();
    }

}
