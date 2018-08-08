package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Arbitro;
import br.com.lufamador.model.EscalaArbitros;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.ArbitroService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/arbitros")
public class ArbitroController {

    private ArbitroService arbitroService;

    @Autowired
    public ArbitroController(ArbitroService arbitroService) {
        this.arbitroService = arbitroService;
    }

    @GetMapping(value = "/load")
    public ResponseEntity<List<Arbitro>> getEscalas() {
        List<Arbitro> list = this.arbitroService.getArbitros();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Page<Arbitro>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<Arbitro>> response = new Response<>();
        Page<Arbitro> agremiacoes = this.arbitroService.findAll(page, count);
        response.setData(agremiacoes);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Arbitro>> findById(@PathVariable("codigo") Long codigo) {
        Response<Arbitro> response = new Response<>();
        Arbitro arbitro = this.arbitroService.findByCodigo(codigo);
        if (null == arbitro) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(arbitro);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Arbitro>> cadastraArbitro(@RequestBody Arbitro arbitro) {
        Response<Arbitro> response = new Response<>();
        final Arbitro arbitroSaved = this.arbitroService.createOrUpdate(arbitro);
        response.setData(arbitroSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Arbitro>> atualizaArbitro(@RequestBody Arbitro arbitro) {
        Response<Arbitro> response = new Response<>();
        final Arbitro arbitroSaved = this.arbitroService.createOrUpdate(arbitro);
        response.setData(arbitroSaved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<?> deletaArbitro(@PathVariable(value = "codigo") Long codigo) {
        this.arbitroService.delete(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
