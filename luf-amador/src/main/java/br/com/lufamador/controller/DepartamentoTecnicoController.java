package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.service.DepartamentoTecnicoService;
import br.com.lufamador.utils.constants.CategoriaConstant;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/departamento-tecnico")
public class DepartamentoTecnicoController {

    private DepartamentoTecnicoService departamentoTecnicoService;

    @Autowired
    public DepartamentoTecnicoController(DepartamentoTecnicoService departamentoTecnicoService) {
        this.departamentoTecnicoService = departamentoTecnicoService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DepartamentoTecnico> cadastraEntidadeDepartamentoTecnico(
            @RequestBody DepartamentoTecnico departamentoTecnico) {
        final DepartamentoTecnico departamentoTecnicoSaved = this.departamentoTecnicoService.cadastraDepartamentoTecnico(
                departamentoTecnico);
        HttpStatus status = (null == departamentoTecnicoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<>(departamentoTecnicoSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<DepartamentoTecnico> editarEntidadeDepartamentoTecnico(
            @RequestBody DepartamentoTecnico departamentoTecnico) {
        final DepartamentoTecnico departamentoTecnicoSaved = this.departamentoTecnicoService.editarDepartamentoTecnico(
                departamentoTecnico);
        HttpStatus status = (null == departamentoTecnicoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicoSaved,
                status);
    }

    @RequestMapping(path = "/{codigo}/", method = RequestMethod.DELETE)
    public ResponseEntity<DepartamentoTecnico> deletarEntidadeDepartamentoTecnico(
            @PathVariable(value = "codigo") Long codigo) {
        this.departamentoTecnicoService.deletarEntidadeDepartamentoTecnico(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/editais", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getEditaisDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoService.getDepartamentoTecnicoList(
                CategoriaConstant.EDITAIS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/comunicados", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getComunicadosDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoService.getDepartamentoTecnicoList(
                CategoriaConstant.COMUNICADOS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/notas-oficiais", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getNotasOficiaisDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoService.getDepartamentoTecnicoList(
                CategoriaConstant.NOTAS_OFICIAIS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/sumulas", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getSumulasDepartamentoTecnicos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoService.getDepartamentoTecnicoList(
                CategoriaConstant.SUMULAS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/artilharia-defesa", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getArtilhariasCampeonatos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoService.getDepartamentoTecnicoList(
                CategoriaConstant.ARTILHARIA_DEFESA.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

}
