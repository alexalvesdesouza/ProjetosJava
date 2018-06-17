package br.com.lufamador.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final String                 ROLE_ESC_ATLETAS      = "ESC_ATLETAS";
  private final String                 ROLE_ESC_AGREMIACOES  = "ESC_AGREMIACOES";
  private final String                 ROLE_ESC_TJDU         = "ESC_TJDU";
  private final String                 ROLE_ESC_TEMPO_REAL   = "ESC_TEMPO_REAL";
  private final String                 ROLE_ESC_DPTO_TECNICO = "ESC_DPTO_TECNICO";

  @Autowired
  private ImplementsUserDetailsService userDetails;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET,
                     "/classificacoes",
                     "/departamento-tecnico/editais",
                     "/departamento-tecnico/comunicados",
                     "/departamento-tecnico/notas-oficiais",
                     "/departamento-tecnico/sumulas",
                     "/departamento-tecnico/artilharia-defesa",
                     "/tabela-jogos",
                     "/tjdus/editais",
                     "/tjdus/portarias",
                     "/tjdus/resultados",
                     "/escalas",
                     "/jogos/tempo-real",
                     "/jogos/resultados")
        .permitAll()
        .antMatchers("/view-atletas")
        .hasAnyRole(ROLE_ESC_ATLETAS)
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .logoutSuccessUrl("/login?logout");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    BCryptPasswordEncoder encoder = passwordEncoder();

    // auth.userDetailsService(userDetails).passwordEncoder(encoder);


    auth.inMemoryAuthentication()
        .withUser("alex")
        .password(encoder.encode("123"))
        .roles(ROLE_ESC_ATLETAS, ROLE_ESC_AGREMIACOES, ROLE_ESC_TJDU, ROLE_ESC_TEMPO_REAL, ROLE_ESC_DPTO_TECNICO);

    auth.inMemoryAuthentication()
        .withUser("celiana")
        .password(encoder.encode("celiana@luf"))
        .roles(ROLE_ESC_ATLETAS, ROLE_ESC_AGREMIACOES, ROLE_ESC_TJDU, ROLE_ESC_TEMPO_REAL, ROLE_ESC_DPTO_TECNICO);

    auth.inMemoryAuthentication()
        .withUser("leticia")
        .password(encoder.encode("leticia@luf"))
        .roles(ROLE_ESC_ATLETAS, ROLE_ESC_AGREMIACOES, ROLE_ESC_TJDU, ROLE_ESC_TEMPO_REAL, ROLE_ESC_DPTO_TECNICO);

    auth.inMemoryAuthentication()
        .withUser("ranier")
        .password(encoder.encode("ranier@luf"))
        .roles(ROLE_ESC_ATLETAS, ROLE_ESC_AGREMIACOES, ROLE_ESC_TJDU, ROLE_ESC_TEMPO_REAL, ROLE_ESC_DPTO_TECNICO);


  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
       .antMatchers("/materialize/**", "/style/**");
  }
}
