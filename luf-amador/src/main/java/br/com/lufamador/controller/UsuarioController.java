package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Usuario;
import br.com.lufamador.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Usuario> cadastraUsuario(@RequestBody Usuario usuario) {
        final Usuario usuarioSaved = this.usuarioService.cadastraUsuario(usuario);
        HttpStatus status = (null == usuarioSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<Usuario>(usuarioSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Usuario> carregarClassificacoes() {
        return usuarioService.getUsuarios();
    }


}
