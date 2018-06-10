package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.service.ClassificacaoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/classificacoes")
public class ClassificacaoController {

    private ClassificacaoService classificacaoService;

    @Autowired
    public ClassificacaoController(ClassificacaoService classificacaoService) {
        this.classificacaoService = classificacaoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    List<Classificacao> carregarClassificacoes() {
        return classificacaoService.getClassificacoes();
    }

}
