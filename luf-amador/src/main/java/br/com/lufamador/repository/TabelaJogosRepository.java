package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lufamador.model.TabelaJogos;

public interface TabelaJogosRepository extends JpaRepository<TabelaJogos, Long> {

 }
