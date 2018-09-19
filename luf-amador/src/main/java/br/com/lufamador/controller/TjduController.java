package br.com.lufamador.controller;

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

import br.com.lufamador.model.MembroTjdu;
import br.com.lufamador.model.Tjdu;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.TjduServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tjdus")
public class TjduController {

    @Autowired
    private TjduServiceImpl tjduService;

    @PostMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Tjdu>> cadastraTjdu(@RequestBody Tjdu tjdu) {
        Response<Tjdu> response = new Response<>();
        final Tjdu entity = this.tjduService.createOrUpdate(tjdu);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/membro")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<MembroTjdu>> cadastraMenbroTjdu(@RequestBody MembroTjdu membroTjdu) {
        Response<MembroTjdu> response = new Response<>();
        final MembroTjdu entity = this.tjduService.createOrUpdate(membroTjdu);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/membro")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<MembroTjdu>> editarMembroTjdu(@RequestBody MembroTjdu membroTjdu) {
        Response<MembroTjdu> response = new Response<>();
        final MembroTjdu entity = this.tjduService.createOrUpdate(membroTjdu);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Tjdu>> editarTjdu(@RequestBody Tjdu tjdu) {
        Response<Tjdu> response = new Response<>();
        final Tjdu entity = this.tjduService.createOrUpdate(tjdu);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<Tjdu>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<Tjdu>> response = new Response<>();
        Page<Tjdu> entitys = this.tjduService.findAll(page, count);
        response.setData(entitys);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "/membro/{page}/{count}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<MembroTjdu>>> findAllMembros(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<MembroTjdu>> response = new Response<>();
        Page<MembroTjdu> entitys = this.tjduService.findAllMembros(page, count);
        response.setData(entitys);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Tjdu>> findById(@PathVariable("codigo") Long codigo) {
        Response<Tjdu> response = new Response<>();
        Tjdu entity = this.tjduService.findByCodigo(codigo);
        if (null == entity) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/membro/{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<MembroTjdu>> findMembroById(@PathVariable("codigo") Long codigo) {
        Response<MembroTjdu> response = new Response<>();
        MembroTjdu entity = this.tjduService.findMembroByCodigo(codigo);
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
    public ResponseEntity<Tjdu> deletarTjdu(@PathVariable(value = "codigo") Long codigo) {
        this.tjduService.delete(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/membro/{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<MembroTjdu> deletarMembroTjdu(@PathVariable(value = "codigo") Long codigo) {
        this.tjduService.deleteMenbroTjdu(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
