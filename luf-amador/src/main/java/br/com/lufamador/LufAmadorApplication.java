package br.com.lufamador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LufAmadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LufAmadorApplication.class, args);
    }

//    @Bean
//    CommandLineRunner init(UserRepository repository, PasswordEncoder encoder) {
//        return args -> {
//            iniUsers(repository, encoder);
//        };
//    }
//
//    private void iniUsers(UserRepository repository, PasswordEncoder encoder) {
//        User admin = new User();
//        admin.setNome("Admin");
//        admin.setEmail("admin@lufamador.com.br");
//        admin.setPassword(encoder.encode("123456"));
//        admin.setProfile(ProfileEnum.ROLE_ADMIN);
//
//        User userFind = repository.findByEmail(admin.getEmail());
//        if (null == userFind)
//            repository.save(admin);
//    }
}
