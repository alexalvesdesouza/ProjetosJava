package br.com.lufamador.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import br.com.lufamador.utils.enums.ProfileEnum;

@Entity
@Table(name = "luf_user")
public class User {

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long        id;

  private String      nome;

//  @NotBlank(message = "Email obrigatorio")
//  @Email(message = "Email inválido")
  private String      email;

//  @NotBlank(message = "Password obrigatorio")
  @Size(min = 6)
  private String      password;

  @Column(columnDefinition = "TEXT")
  private String      image;

  private ProfileEnum profile;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ProfileEnum getProfile() {
    return profile;
  }

  public void setProfile(ProfileEnum profile) {
    this.profile = profile;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
  
  

}
