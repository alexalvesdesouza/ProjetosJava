package com.ideaapi.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideaapi.model.Usuario;
import com.ideaapi.repository.UsuarioRepository;
import com.ideaapi.repository.filter.UsuarioFilter;

@Service
public class UsuarioService {

    private Random rand = SecureRandom.getInstanceStrong();

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioService() throws NoSuchAlgorithmException { //NOSONAR
    }

    public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
        final Page<Usuario> filtrar = this.usuarioRepository.filtrar(filter, pageable);
        filtrar.iterator().forEachRemaining(usuario -> usuario.setSenha("*******"));

        return filtrar;
    }

    public Usuario cadastraUsuario(Usuario entity) {
        final String senha = senhaRandomica();
        entity.setSenha(new BCryptPasswordEncoder().encode(senha));
        Usuario usuarioSalvo = this.usuarioRepository.save(entity);
        this.enviaEmail(usuarioSalvo, senha);
        usuarioSalvo.setSenha("*******");
        return usuarioSalvo;
    }

    private void enviaEmail(Usuario usuarioSalvo, String senha) {
        System.out.println(usuarioSalvo.getEmail()+senha); //NOSONAR
    }

    private String senhaRandomica() {

        String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ";

        StringBuilder sb = new StringBuilder();
        int index = -1;
        for (int i = 0; i < 6; i++) {
            index = this.rand.nextInt(letras.length());
            sb.append(letras.substring(index, index + 1)).append(index);
        }

        return sb.toString();
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
