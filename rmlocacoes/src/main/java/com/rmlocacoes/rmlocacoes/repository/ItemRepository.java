package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
  Item findByCodigo(Long codigo);
}
