package com.rmlocacoes.rmlocacoes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.rmlocacoes.rmlocacoes.model.Usuario;
import com.rmlocacoes.rmlocacoes.repository.UsuarioRepository;

@Repository
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UsuarioRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    Usuario user = userRepository.findByLogin(login);

    if (null == user) {
      throw new UsernameNotFoundException("Usuário não encontrato.");
    }

    return user;
  }

}
