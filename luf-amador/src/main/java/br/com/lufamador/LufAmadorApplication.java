package br.com.lufamador;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.lufamador.model.Permissao;
import br.com.lufamador.model.Usuario;
import br.com.lufamador.repository.UsuarioRepository;

@SpringBootApplication
public class LufAmadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LufAmadorApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {
            iniUsers(repository, encoder);
        };
    }

    private void iniUsers(UsuarioRepository repository, PasswordEncoder encoder) {
        Usuario admin = new Usuario();
        admin.setNome("Admin");
        admin.setEmail("admin@lufamador.com.br");
        admin.setSenha(encoder.encode("123456"));
        Permissao permissao = new Permissao();
        permissao.setDescricao("ROLE_ADMIN");
        admin.setPermissoes(Arrays.asList(permissao));

        Optional<Usuario> userFind = repository.findByEmail(admin.getEmail());
        if (!userFind.isPresent())
            repository.save(admin);
    }
}
