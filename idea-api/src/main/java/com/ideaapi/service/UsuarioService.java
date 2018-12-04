package com.ideaapi.service;

import static com.ideaapi.constansts.ErrorsCode.RECURSO_NAO_ENCONTRADO;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideaapi.exceptions.BusinessException;
import com.ideaapi.mail.EnvioEmail;
import com.ideaapi.model.SenhaAlterar;
import com.ideaapi.model.SenhaReiniciar;
import com.ideaapi.model.Usuario;
import com.ideaapi.repository.UsuarioRepository;
import com.ideaapi.repository.filter.UsuarioFilter;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnvioEmail envioEmail;

    public Page<Usuario> filtrar(UsuarioFilter filter, Pageable pageable) {
        final Page<Usuario> filtrar = this.usuarioRepository.filtrar(filter, pageable);
        filtrar.iterator().forEachRemaining(usuario -> usuario.setSenha("*******"));

        return filtrar;
    }

    public Usuario cadastraUsuario(Usuario entity) {
        final String senha = senhaRandomica();
        entity.setSenha(new BCryptPasswordEncoder().encode(senha));
        Usuario usuarioSalvo = this.usuarioRepository.save(entity);
        this.enviaEmailSenhaGerada(usuarioSalvo, senha);
        usuarioSalvo.setSenha("*******");
        return usuarioSalvo;
    }


    private void enviaEmailSenhaGerada(Usuario usuario, String senha) {

        String template = "email/envio-senha";

        Map<String, Object> map = new HashMap<>();
        map.put("senha", senha);

        this.envioEmail.enviarEmail("openlinkti@gmail.com", Arrays.asList(usuario.getEmail()),
                "Senha de Acesso ao Sistema Ideia", template, map);
    }

    private String senhaRandomica() {


        StringBuilder sb = new StringBuilder();
        Random gerador = new Random(); //NOSONAR

        for (int i = 0; i < 6; i++) {
            sb.append(gerador.nextInt(9));
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

    public Usuario buscaUsuarioPorEmail(String email) {
        Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return usuario.get();
        }

        return null;
    }

    public void deletaUsuario(Long codigo) {
        this.usuarioRepository.delete(codigo);
    }

    public ResponseEntity<Usuario> alterarSenhaUsuario(Long codigo, SenhaAlterar senhaAlterar) {
        Usuario usuario = this.usuarioRepository.findOne(codigo);

        if (usuario != null) {
            usuario.setSenha(senhaAlterar.getSenhaNova());
            this.usuarioRepository.save(usuario);
            this.envioEmail.enviarEmail("openlinkti@gmail.com", Collections.singletonList(usuario.getEmail()),
                    "Senha de Acesso ao Sistema Ideia Alterado com sucesso", "email/alterar-senha", null);
            return ResponseEntity.ok(usuario);

        } else {
            throw new BusinessException(RECURSO_NAO_ENCONTRADO);
        }
    }

    public ResponseEntity reiniciarSenhaUsuario(SenhaReiniciar senhaReiniciar) {
        Usuario usuario = this.buscaUsuarioPorEmail(senhaReiniciar.getEmail());

        if (usuario != null) {

            this.envioEmail.enviarEmail("openlinkti@gmail.com", Collections.singletonList(usuario.getEmail()),
                    "Senha de Acesso ao Sistema Ideia Reiniciada", "email/reiniciar-senha", null);

            return new ResponseEntity(HttpStatus.OK);

        } else {
            throw new BusinessException(RECURSO_NAO_ENCONTRADO);
        }
    }
}
