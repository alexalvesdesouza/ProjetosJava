package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Campeonato;
import br.com.lufamador.model.Classificacao;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.ClassificacaoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/classificacoes")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

    @GetMapping(value = "/{page}/{count}/{categoria}/{chave}/list")
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Response<List<Classificacao>>> carregarClassificacoes(@PathVariable("page") int page,
            @PathVariable("count") int count, @PathVariable(value = "categoria") String categoria,
            @PathVariable(value = "chave") String chave) {

        Response<List<Classificacao>> response = new Response<>();
        final List<Classificacao> classificacoes = classificacaoService.loadClassificacaoPorCategoria(categoria, chave);
        response.setData(classificacoes);

        return ResponseEntity.ok(response);

    }

    @PutMapping(value = "/fecha-classificacao")
    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    public ResponseEntity<Response<List<Classificacao> >> atualizaClassificacao(@RequestBody List<Classificacao> classificacoes) {
        Response<List<Classificacao> > response = new Response<>();
        final List<Classificacao>  entity = this.classificacaoService.finalizaClassificacaoPorFase(classificacoes);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

}
