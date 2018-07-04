package br.com.lufamador.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.User;
import br.com.lufamador.security.jwt.JwtUserFactory;
import br.com.lufamador.service.impl.UserServiceImpl;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  private final UserServiceImpl userService;

  @Autowired
  public JwtUserDetailsServiceImpl(UserServiceImpl userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    User user = userService.findByEmail(email);
    if (null == user)
      throw new UsernameNotFoundException("Usuário não encontrado");

    return JwtUserFactory.create(user);
  }

}
