package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.EscalaArbitros;
import br.com.lufamador.repository.EscalaArbitrosRepository;

@Service
public class EscalaArbitrosServiceImpl {

    @Autowired
    private EscalaArbitrosRepository repository;

    public List<EscalaArbitros> getEscalaArbitrosList() {
        return this.repository.findAll();
    }

}
