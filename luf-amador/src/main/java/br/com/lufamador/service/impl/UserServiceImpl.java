package br.com.lufamador.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufamador.repository.UserRepository;
import br.com.lufamador.service.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;

  @Override
  public User findByEmail(String email) {
    return this.repository.findByEmail(email);
  }

  @Override
  public User createOrUpdate(User user) {
    return this.repository.save(user);
  }

  @Override
  public User findById(Long id) {
    Optional<User> findById = this.repository.findById(id);
    return findById.get();
  }

  @Override
  public void delete(Long id) {
    this.repository.deleteById(id);
  }

  @Override
  public Page<User> findAll(int page, int count) {
    Pageable pages = PageRequest.of(page, count, Sort.Direction.ASC, "nome");
    return this.repository.findAll(pages);
  }

}
