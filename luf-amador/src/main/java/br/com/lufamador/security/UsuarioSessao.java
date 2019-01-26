package br.com.lufamador.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Usuario;
import br.com.lufamador.service.UsuarioService;

@Component
public class UsuarioSessao {

    private static UsuarioService usuarioService;

    @Autowired
    public UsuarioSessao(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public static Long getCodUsuario() {
        return null;
    }

    public static String getUsername() {
        return null;
    }

    public static Usuario getUserLogado() {

        Authentication authentication = getAuthentication();

        if (authentication != null) {
            final Object principal = authentication.getPrincipal();
            String email = (String) principal;
            return usuarioService.buscaUsuarioPorEmail(email);
        }

        return null;
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
