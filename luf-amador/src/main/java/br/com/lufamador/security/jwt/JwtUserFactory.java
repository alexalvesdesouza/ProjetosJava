package br.com.lufamador.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.lufamador.model.User;
import br.com.lufamador.utils.enums.ProfileEnum;

public class JwtUserFactory {

  private JwtUserFactory() {}

  public static JwtUser create(User user) {
    return new JwtUser(user.getId(),
                       user.getEmail(),
                       user.getPassword(),
                       mapToGrantedAuthorities(user.getProfile()));
  }

  private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
    return authorities;
  }

}
