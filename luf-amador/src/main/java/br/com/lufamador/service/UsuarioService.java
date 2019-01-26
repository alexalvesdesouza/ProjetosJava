package br.com.lufamador.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.lufamador.dto.SenhaAlterar;
import br.com.lufamador.dto.SenhaReiniciar;
import br.com.lufamador.model.Usuario;
import br.com.lufamador.repository.filter.UsuarioFilter;

public interface UsuarioService {

    Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable);

    Usuario cadastraUsuario(Usuario entity);

    ResponseEntity<Usuario> atualizaUsuario(Long codigo, Usuario usuario);

    Usuario buscaUsuario(Long codigo);

    Usuario buscaUsuarioPorEmail(String email);

    Optional<Usuario> findByEmail(String email);

    void deletaUsuario(Long codigo);

    ResponseEntity<Usuario> alterarSenhaUsuario(Long codigo, SenhaAlterar senhaAlterar);

    ResponseEntity reiniciarSenhaUsuario(SenhaReiniciar senhaReiniciar);

}
