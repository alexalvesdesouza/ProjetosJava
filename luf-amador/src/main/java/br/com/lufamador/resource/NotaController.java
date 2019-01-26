package br.com.lufamador.resource;

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

import br.com.lufamador.model.Nota;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.NotaService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole({'FINANCEIRO', 'ADMIN'})")
    public ResponseEntity<Response<Page<Nota>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {
        Response<Page<Nota>> response = new Response<Page<Nota>>();
        Page<Nota> notas = this.notaService.findAll(page, count);
        response.setData(notas);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'FINANCEIRO', 'ADMIN'})")
    public ResponseEntity<Response<Nota>> findById(@PathVariable("codigo") Long codigo) {
        Response<Nota> response = new Response<>();
        Nota nota = this.notaService.findByCodigo(codigo);
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
    @PreAuthorize("hasAnyRole({'FINANCEIRO', 'ADMIN'})")
    public ResponseEntity<Response<Nota>> cadastraNota(@RequestBody Nota nota) {
        Response<Nota> response = new Response<>();
        final Nota notaSaved = this.notaService.createOrUpdate(nota);
        response.setData(notaSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole({'FINANCEIRO', 'ADMIN'})")
    public ResponseEntity<Response<Nota>> atualizaNota(@RequestBody Nota nota) {
        Response<Nota> response = new Response<>();
        final Nota notaSaved = this.notaService.createOrUpdate(nota);
        response.setData(notaSaved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'FINANCEIRO', 'ADMIN'})")
    public ResponseEntity<?> deletaNota(@PathVariable("codigo") Long codigo) {
        this.notaService.delete(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

}
