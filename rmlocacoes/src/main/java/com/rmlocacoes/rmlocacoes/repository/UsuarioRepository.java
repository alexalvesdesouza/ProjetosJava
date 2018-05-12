package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	Usuario findByLogin(String login);

}
