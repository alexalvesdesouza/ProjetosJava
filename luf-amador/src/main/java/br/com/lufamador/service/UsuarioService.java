package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Usuario;
import br.com.lufamador.repository.UsuarioRepository;
import br.com.lufamador.validate.UsuarioValidate;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioValidate validate;

    @Autowired
    public UsuarioService(UsuarioRepository repository, UsuarioValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Usuario cadastraUsuario(Usuario usuario) {
        Usuario usuarioSaved = null;
        this.validate.validaUsuarioExistente(usuario);
        try {
            usuarioSaved = this.repository.saveAndFlush(usuario);
        } catch (Exception e) {

        }
        return usuarioSaved;
    }

    public List<Usuario> getUsuarios() {
        return this.repository.findAll();
    }
    
    public Page<Usuario> findAll(int page, int count) {
      Pageable pages = PageRequest.of(page, count);
      return this.repository.findAll(pages);
    }
}
