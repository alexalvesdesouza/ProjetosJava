package br.com.helpdesk.api.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMananagerProvider  {

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return authenticationManagerBean();
  }

}
