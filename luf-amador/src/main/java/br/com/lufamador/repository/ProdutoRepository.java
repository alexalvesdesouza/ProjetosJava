package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
