package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.ClassificacaoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/classificacoes")
public class ClassificacaoController {

    private ClassificacaoService classificacaoService;

    @Autowired
    public ClassificacaoController(ClassificacaoService classificacaoService) {
        this.classificacaoService = classificacaoService;
    }

    @GetMapping(value = "/load")
    public ResponseEntity<List<Classificacao>> getClassificacoes() {
        List<Classificacao> list = this.classificacaoService.getClassificacoes();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{page}/{count}/list")
    @PreAuthorize("hasAnyRole('ADM_JOGOS')")
    public ResponseEntity<Response<List<Classificacao>>> carregarClassificacoes(@PathVariable("page") int page,
            @PathVariable("count") int count) {
        Response<List<Classificacao>> response = new Response<>();
        final List<Classificacao> classificacoes = classificacaoService.getClassificacoes();
        response.setData(classificacoes);
        return ResponseEntity.ok(response);

    }

}
