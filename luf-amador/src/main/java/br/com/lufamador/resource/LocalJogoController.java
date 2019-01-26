package br.com.lufamador.resource;

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

import br.com.lufamador.model.LocalJogo;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.LocalJogoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/locais-jogos")
public class LocalJogoController {

    @Autowired
    private LocalJogoService localJogoService;

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<LocalJogo>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<LocalJogo>> response = new Response<>();
        Page<LocalJogo> agremiacoes = this.localJogoService.findAll(page, count);
        response.setData(agremiacoes);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<LocalJogo>> findById(@PathVariable("codigo") Long codigo) {
        Response<LocalJogo> response = new Response<>();
        LocalJogo localJogo = this.localJogoService.findByCodigo(codigo);
        if (null == localJogo) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(localJogo);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<LocalJogo>> cadastraLocalJogo(@RequestBody LocalJogo localJogo) {
        Response<LocalJogo> response = new Response<>();
        final LocalJogo localJogoSaved = this.localJogoService.createOrUpdate(localJogo);
        response.setData(localJogoSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<LocalJogo>> atualizaLocalJogo(@RequestBody LocalJogo localJogo) {
        Response<LocalJogo> response = new Response<>();
        final LocalJogo localJogoSaved = this.localJogoService.createOrUpdate(localJogo);
        response.setData(localJogoSaved);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping(path = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<?> deletaLocalJogo(@PathVariable(value = "codigo") Long codigo) {
        this.localJogoService.delete(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
