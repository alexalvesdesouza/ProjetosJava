package com.algaworks.algamoneyapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listaTodasCategorias() {
        return this.categoriaRepository.findAll();
    }

    public Categoria cadastraCategoria(Categoria entity) {
        return this.categoriaRepository.save(entity);
    }

    public Categoria buscaCategoria(Long codigo) {
        Categoria categoria = this.categoriaRepository.findOne(codigo);
        if (categoria != null ) {
            return categoria;
        }

        return null;
    }

    public void deletaCategoria(Long codigo) {
        this.categoriaRepository.delete(codigo);
    }
}
