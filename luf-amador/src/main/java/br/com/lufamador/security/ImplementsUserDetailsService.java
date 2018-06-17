package br.com.lufamador.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.lufamador.model.Usuario;
import br.com.lufamador.repository.UsuarioRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByLogin(username);
    if (null == usuario)
      throw new UsernameNotFoundException("Usuário não encontrado");

    return new User(usuario.getUsername(),
                    usuario.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    usuario.getAuthorities());
  }

}
