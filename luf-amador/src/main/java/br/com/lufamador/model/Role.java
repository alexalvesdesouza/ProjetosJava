package br.com.lufamador.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "luf_role")
public class Role implements GrantedAuthority {

  private static final long serialVersionUID = 1L;

  @Id
  private String            nomeRole;

  @ManyToMany
  private List<User>     usuarios;

  @Override
  public String getAuthority() {
    // TODO Auto-generated method stub
    return this.nomeRole;
  }

  public String getNomeRole() {
    return nomeRole;
  }

  public void setNomeRole(String nomeRole) {
    this.nomeRole = nomeRole;
  }

  public List<User> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<User> usuarios) {
    this.usuarios = usuarios;
  }



}
