package com.hibicode.beerstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hibicode.beerstore.model.Beer;

public interface BeerRepository extends JpaRepository<Beer, Long> {
}
