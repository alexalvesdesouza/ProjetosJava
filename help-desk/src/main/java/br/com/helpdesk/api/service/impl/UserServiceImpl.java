package br.com.helpdesk.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.repository.UserRepository;
import br.com.helpdesk.api.service.UserService;

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
  public User findById(String id) {
    Optional<User> findById = this.repository.findById(id);
    return findById.get();
  }

  @Override
  public void delete(String id) {
    this.repository.deleteById(id);
  }

  @Override
  public Page<User> findAll(int page, int size) {
    // Pageable pages = (Pageable) new PageRequest(page,
    // size);
    // return this.repository.findAll();
    return null;
  }

}
