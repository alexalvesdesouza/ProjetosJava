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

import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.DepartamentoTecnicoServiceImpl;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/departamento-tecnico")
public class DepartamentoTecnicoController {

    @Autowired
    private DepartamentoTecnicoServiceImpl departamentoTecnicoServiceImpl;

    @PostMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<DepartamentoTecnico>> cadastrar(
            @RequestBody DepartamentoTecnico departamentoTecnico) {
        Response<DepartamentoTecnico> response = new Response<>();
        final DepartamentoTecnico departamentoTecnicoSaved = this.departamentoTecnicoServiceImpl.createOrUpdate(
                departamentoTecnico);
        response.setData(departamentoTecnicoSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<DepartamentoTecnico>> atualizar(
            @RequestBody DepartamentoTecnico departamentoTecnico) {
        Response<DepartamentoTecnico> response = new Response<>();
        final DepartamentoTecnico departamentoTecnicoSaved = this.departamentoTecnicoServiceImpl.createOrUpdate(
                departamentoTecnico);
        response.setData(departamentoTecnicoSaved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<DepartamentoTecnico> deletarEntidadeDepartamentoTecnico(
            @PathVariable(value = "codigo") Long codigo) {
        this.departamentoTecnicoServiceImpl.deletarEntidadeDepartamentoTecnico(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<DepartamentoTecnico>> findById(@PathVariable("codigo") Long codigo) {
        Response<DepartamentoTecnico> response = new Response<>();
        DepartamentoTecnico entity = this.departamentoTecnicoServiceImpl.findByCodigo(codigo);
        if (null == entity) {
            response.getErrors()
                    .add("Register not found " + codigo);
            return ResponseEntity.badRequest()
                    .body(response);
        }
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{page}/{count}")
    @PreAuthorize("hasAnyRole({'SECRETARIA', 'ADMIN'})")
    public ResponseEntity<Response<Page<DepartamentoTecnico>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<DepartamentoTecnico>> response = new Response<>();
        Page<DepartamentoTecnico> entitys = this.departamentoTecnicoServiceImpl.findAll(page, count);
        response.setData(entitys);
        return ResponseEntity.ok(response);

    }

}
