package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
        if (categoria.isPresent()) {
            return categoria.get();
        }

        return null;
    }

    public void deletaCategoria(Long codigo) {
        this.categoriaRepository.deleteById(codigo);
    }
}
