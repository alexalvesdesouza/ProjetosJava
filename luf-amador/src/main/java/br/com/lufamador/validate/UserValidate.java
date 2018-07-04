package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.repository.UserRepository;

@Component
public class UserValidate {

  private final UserRepository repository;

  @Autowired
  public UserValidate(UserRepository repository) {
    this.repository = repository;
  }

}
