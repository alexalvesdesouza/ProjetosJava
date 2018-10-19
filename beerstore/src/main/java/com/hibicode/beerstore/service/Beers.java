package com.hibicode.beerstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.repository.BeerRepository;

@Service
public class Beers {

    @Autowired
    private BeerRepository beerRepository;

    public Beer save(Beer beer) {
        return this.beerRepository.saveAndFlush(beer);
    }

    public List<Beer> findAll() {
        return this.beerRepository.findAll();
    }
}
