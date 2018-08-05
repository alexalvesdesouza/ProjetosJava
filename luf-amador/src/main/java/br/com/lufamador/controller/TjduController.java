package br.com.lufamador.controller;

import java.util.List;

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

import br.com.lufamador.model.Tjdu;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.TjduServiceImpl;
import br.com.lufamador.utils.constants.CategoriaConstant;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/tjdus")
public class TjduController {

    private TjduServiceImpl tjduService;

    @Autowired
    public TjduController(TjduServiceImpl tjduService) {
        this.tjduService = tjduService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Tjdu>> cadastraTjdu(@RequestBody Tjdu tjdu) {
        Response<Tjdu> response = new Response<>();
        final Tjdu entity = this.tjduService.createOrUpdate(tjdu);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Tjdu>> editarTjdu(@RequestBody Tjdu tjdu) {
        Response<Tjdu> response = new Response<>();
        final Tjdu entity = this.tjduService.createOrUpdate(tjdu);
        response.setData(entity);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<Page<Tjdu>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<Tjdu>> response = new Response<>();
        Page<Tjdu> entitys = this.tjduService.findAll(page, count);
        response.setData(entitys);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
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


    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Tjdu> deletarTjdu(@PathVariable(value = "codigo") Long codigo) {
        this.tjduService.delete(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/editais")
    public ResponseEntity<List<Tjdu>> getEditaisTjdus() {
        final List<Tjdu> tjdus = this.tjduService.getTjduList(CategoriaConstant.EDITAIS.name());
        HttpStatus status = (null == tjdus) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(tjdus, status);
    }

    @GetMapping(value = "/portarias")
    public ResponseEntity<List<Tjdu>> getPortatiasTjdus() {
        final List<Tjdu> tjdus = this.tjduService.getTjduList(CategoriaConstant.PORTARIAS.name());
        HttpStatus status = (null == tjdus) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(tjdus, status);
    }

    @GetMapping(value = "/resultados")
    public ResponseEntity<List<Tjdu>> getTjdus() {
        final List<Tjdu> tjdus = this.tjduService.getTjduList(CategoriaConstant.RESULTADOS.name());
        HttpStatus status = (null == tjdus) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(tjdus, status);
    }

}
