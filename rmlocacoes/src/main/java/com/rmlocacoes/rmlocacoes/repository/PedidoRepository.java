package com.rmlocacoes.rmlocacoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rmlocacoes.rmlocacoes.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
  
  Pedido findByCodigo(Long codigo);

  @Query(value = "select * from rm_pedido limit 100 offset ?1", nativeQuery = true)
  List<Pedido> findAllPedidos(Long page);
}
