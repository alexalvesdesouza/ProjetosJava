package br.com.lufamador.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.ClassificacaoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/classificacoes")
public class ClassificacaoController {

    @Autowired
    private ClassificacaoService classificacaoService;

    @GetMapping(value = "/{page}/{count}/{categoria}/{chave}/{fase}/list")
//    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<List<Classificacao>>> carregarClassificacoes(@PathVariable("page") int page,
            @PathVariable("count") int count, @PathVariable(value = "categoria") String categoria,
            @PathVariable(value = "chave") String chave, @PathVariable(value = "fase") String fase) {

        Response<List<Classificacao>> response = new Response<>();
        final List<Classificacao> classificacoes = classificacaoService.loadClassificacaoPorCategoria(categoria, chave,
                fase);
        response.setData(classificacoes);

        return ResponseEntity.ok(response);

    }

    @PutMapping(value = "/fecha-classificacao")
//    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<List<Classificacao>>> atualizaClassificacao(
            @RequestBody List<Classificacao> classificacoes) {
        Response<List<Classificacao>> response = new Response<>();
        final List<Classificacao> entity = this.classificacaoService.finalizaClassificacaoPorFase(classificacoes);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/classifica")
//    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<Classificacao>> atualizaClassificacao(@RequestBody Classificacao classificacao) {
        Response<Classificacao> response = new Response<>();
        final Classificacao entity = this.classificacaoService.classificaAgremiacao(classificacao);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }


    @PutMapping(value = "/edit")
//    @PreAuthorize("hasAnyRole({'ADM_JOGOS', 'ADMIN'})")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Response<Classificacao>> editaClassificacao(@RequestBody Classificacao classificacao) {
        Response<Classificacao> response = new Response<>();
        final Classificacao entity = this.classificacaoService.editaResultadoClassificacao(classificacao);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

}
