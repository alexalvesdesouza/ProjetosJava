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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.EscalaArbitros;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.EscalaArbitrosServiceImpl;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/escalas")
public class EscalaArbitrosController {

    @Autowired
    private EscalaArbitrosServiceImpl escalaArbitrosService;

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<EscalaArbitros>> cadastraEscalaArbitros(@RequestBody EscalaArbitros escalaArbitros) {
        Response<EscalaArbitros> response = new Response<>();
        final EscalaArbitros entity = this.escalaArbitrosService.createOrUpdate(escalaArbitros);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<EscalaArbitros>> atualizarEscalaArbitros(
            @RequestBody EscalaArbitros escalaArbitros) {
        Response<EscalaArbitros> response = new Response<>();
        final EscalaArbitros entity = this.escalaArbitrosService.createOrUpdate(escalaArbitros);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{page}/{count}/list")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<EscalaArbitros>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<EscalaArbitros>> response = new Response<>();
        Page<EscalaArbitros> entitys = this.escalaArbitrosService.findAll(page, count);
        response.setData(entitys);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/{codigo}/find")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<EscalaArbitros>> findById(@PathVariable("codigo") Long codigo) {
        Response<EscalaArbitros> response = new Response<>();
        EscalaArbitros entity = this.escalaArbitrosService.findByCodigo(codigo);
        if (null == entity) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity deletaInscricao(@PathVariable("codigo") Long codigo) {
        this.escalaArbitrosService.excluirEscala(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

}
