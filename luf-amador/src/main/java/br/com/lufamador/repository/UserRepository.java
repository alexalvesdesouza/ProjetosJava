package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

}
