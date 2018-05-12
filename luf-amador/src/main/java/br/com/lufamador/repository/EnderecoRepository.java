package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
