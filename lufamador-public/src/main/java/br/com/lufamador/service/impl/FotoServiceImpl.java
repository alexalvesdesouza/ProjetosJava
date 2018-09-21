package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Foto;
import br.com.lufamador.repository.FotoRepository;

@Service
public class FotoServiceImpl {

    @Autowired
    private FotoRepository repository;

    public List<Foto> findAll() {
        return this.repository.findAll();
    }

}
