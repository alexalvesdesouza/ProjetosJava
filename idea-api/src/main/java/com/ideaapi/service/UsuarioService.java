package com.ideaapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ideaapi.model.Usuario;
import com.ideaapi.repository.UsuarioRepository;
import com.ideaapi.repository.filter.UsuarioFilter;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
        final Page<Usuario> filtrar = this.usuarioRepository.filtrar(filter, pageable);
        filtrar.iterator().forEachRemaining(usuario -> {
            usuario.setSenha("*******");
        });

        return filtrar;
    }

    public Usuario cadastraUsuario(Usuario entity) {
        return this.usuarioRepository.save(entity);
    }

    public ResponseEntity<Usuario> atualizaUsuario(Long codigo, Usuario usuario) {
        Usuario usuarioSalvo = this.buscaUsuario(codigo);
        BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");

        this.usuarioRepository.save(usuarioSalvo);
        return ResponseEntity.ok(usuarioSalvo);
    }

    public Usuario buscaUsuario(Long codigo) {
        Usuario usuario = this.usuarioRepository.findOne(codigo);
        if (usuario != null) {
            return usuario;
        }

        return null;
    }

    public void deletaUsuario(Long codigo) {
        this.usuarioRepository.delete(codigo);
    }
}
