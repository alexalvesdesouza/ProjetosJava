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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.response.Response;
import br.com.lufamador.service.impl.DepartamentoTecnicoServiceImpl;
import br.com.lufamador.utils.constants.CategoriaConstant;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/departamento-tecnico")
public class DepartamentoTecnicoController {

    private DepartamentoTecnicoServiceImpl departamentoTecnicoServiceImpl;

    @Autowired
    public DepartamentoTecnicoController(DepartamentoTecnicoServiceImpl departamentoTecnicoServiceImpl) {
        this.departamentoTecnicoServiceImpl = departamentoTecnicoServiceImpl;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<DepartamentoTecnico>> cadastrar(
            @RequestBody DepartamentoTecnico departamentoTecnico) {
        Response<DepartamentoTecnico> response = new Response<>();
        final DepartamentoTecnico departamentoTecnicoSaved = this.departamentoTecnicoServiceImpl.createOrUpdate(
                departamentoTecnico);
        response.setData(departamentoTecnicoSaved);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<Response<DepartamentoTecnico>> atualizar(
            @RequestBody DepartamentoTecnico departamentoTecnico) {
        Response<DepartamentoTecnico> response = new Response<>();
        final DepartamentoTecnico departamentoTecnicoSaved = this.departamentoTecnicoServiceImpl.createOrUpdate(
                departamentoTecnico);
        response.setData(departamentoTecnicoSaved);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
    public ResponseEntity<DepartamentoTecnico> deletarEntidadeDepartamentoTecnico(
            @PathVariable(value = "codigo") Long codigo) {
        this.departamentoTecnicoServiceImpl.deletarEntidadeDepartamentoTecnico(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/editais")
    public ResponseEntity<List<DepartamentoTecnico>> getEditaisDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.EDITAIS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/comunicados", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getComunicadosDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.COMUNICADOS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/notas-oficiais", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getNotasOficiaisDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.NOTAS_OFICIAIS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/sumulas", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getSumulasDepartamentoTecnicos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.SUMULAS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/artilharia-defesa", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getArtilhariasCampeonatos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.ARTILHARIA_DEFESA.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/tabelas-jogos", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getTabelasJogos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.TABELA_JOGOS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @GetMapping(value = "{page}/{count}")
    public ResponseEntity<Response<Page<DepartamentoTecnico>>> findAll(@PathVariable("page") int page,
            @PathVariable("count") int count) {

        Response<Page<DepartamentoTecnico>> response = new Response<>();
        Page<DepartamentoTecnico> entitys = this.departamentoTecnicoServiceImpl.findAll(page, count);
        response.setData(entitys);
        return ResponseEntity.ok(response);

    }

    @GetMapping(value = "{codigo}")
    @PreAuthorize("hasAnyRole('SECRETARIA')")
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

}
