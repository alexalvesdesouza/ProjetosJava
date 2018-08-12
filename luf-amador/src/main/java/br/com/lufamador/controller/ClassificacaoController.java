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

    @GetMapping(value = "/{categoria}/load")
    public ResponseEntity<List<Classificacao>> getClassificacoes(@PathVariable(value = "categoria") String categoria) {
        List<Classificacao> list = this.classificacaoService.loadClassificacaoPorCategoria(categoria);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{page}/{count}/{categoria}/list")
    @PreAuthorize("hasAnyRole('ADM_JOGOS')")
    public ResponseEntity<Response<List<Classificacao>>> carregarClassificacoes(@PathVariable("page") int page,
            @PathVariable("count") int count, @PathVariable(value = "categoria") String categoria) {
        Response<List<Classificacao>> response = new Response<>();
        final List<Classificacao> classificacoes = classificacaoService.loadClassificacaoPorCategoria(categoria);
        response.setData(classificacoes);
        return ResponseEntity.ok(response);

    }

}
