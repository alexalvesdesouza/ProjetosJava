package br.com.lufamador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.lufamador.model.Produto;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.ProdutoService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole('FINANCEIRO')")
    public ResponseEntity<Response<Page<Produto>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {
        Response<Page<Produto>> response = new Response<>();
        Page<Produto> notas = this.produtoService.findAll(page, count);
        response.setData(notas);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('FINANCEIRO')")
    public ResponseEntity<Response<Produto>> findById(@PathVariable("codigo") Long codigo) {
        Response<Produto> response = new Response<>();
        Produto nota = this.produtoService.findByCodigo(codigo);
        if (null == nota) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(nota);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('FINANCEIRO')")
    public ResponseEntity<Response<Produto>> cadastraProduto(@RequestBody Produto nota) {
        Response<Produto> response = new Response<>();
        final Produto notaSaved = this.produtoService.createOrUpdate(nota);
        response.setData(notaSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('FINANCEIRO')")
    public ResponseEntity<Response<Produto>> atualizaProduto(@RequestBody Produto nota) {
        Response<Produto> response = new Response<>();
        final Produto notaSaved = this.produtoService.createOrUpdate(nota);
        response.setData(notaSaved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('FINANCEIRO')")
    public ResponseEntity<?> deletaProduto(@PathVariable("codigo") Long codigo) {
        this.produtoService.delete(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

}
