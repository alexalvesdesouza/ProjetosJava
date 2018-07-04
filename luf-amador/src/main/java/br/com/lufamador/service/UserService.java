package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.User;

public interface UserService {

  User findByEmail(String email);

  User createOrUpdate(User user);

  User findById(Long id);

  void delete(Long id);

  Page<User> findAll(int page, int count);

}
