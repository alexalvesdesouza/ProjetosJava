package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.ClassificacaoHistory;

public interface ClassificacaoHistoryRepository extends JpaRepository<ClassificacaoHistory, Long> {

}
