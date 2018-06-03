package br.com.lufamador.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    BCryptPasswordEncoder encoder = passwordEncoder();
//    auth.inMemoryAuthentication()
//        .withUser("alex")
//        .password(encoder.encode("123"))
//        .roles("ADMIN");
//  }
//
//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }

}
