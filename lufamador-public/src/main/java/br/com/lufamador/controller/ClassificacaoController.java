package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.service.impl.ClassificacaoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/classificacoes")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

    @GetMapping(value = "/{categoria}/{chave}/load")
    public ResponseEntity<List<Classificacao>> getClassificacoes(@PathVariable(value = "categoria") String categoria,
            @PathVariable(value = "chave") String chave) {
        List<Classificacao> list = this.classificacaoService.loadClassificacaoPorCategoriaChave(categoria, chave);
        return ResponseEntity.ok(list);
    }

}
