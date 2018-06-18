package br.com.helpdesk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.helpdesk.api.entity.User;
import br.com.helpdesk.api.enums.ProfileEnum;
import br.com.helpdesk.api.repository.UserRepository;

@SpringBootApplication
public class HelpDeskApplication {

  public static void main(String[] args) {
    SpringApplication.run(HelpDeskApplication.class, args);
  }

  @Bean
  CommandLineRunner init(UserRepository repository, PasswordEncoder encoder) {
    return args -> {
      iniUsers(repository, encoder);
    };
  }

  private void iniUsers(UserRepository repository, PasswordEncoder encoder) {
    User admin = new User();
    admin.setEmail("admin@helpdesk.com.br");
    admin.setPassword(encoder.encode("123456"));
    admin.setProfile(ProfileEnum.ROLE_ADMIN);

    User userFind = repository.findByEmail(admin.getEmail());
    if (null == userFind)
      repository.save(admin);
  }
}
