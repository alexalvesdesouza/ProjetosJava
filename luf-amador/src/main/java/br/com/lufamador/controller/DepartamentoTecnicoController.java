package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.service.DepartamentoTecnicoService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/departamentos-tecnico")
public class DepartamentoTecnicoController {

    private DepartamentoTecnicoService departamentoTecnicoService;

    @Autowired
    public DepartamentoTecnicoController(DepartamentoTecnicoService departamentoTecnicoService) {
        this.departamentoTecnicoService = departamentoTecnicoService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DepartamentoTecnico> cadastraDepartamentoTecnico(
            @RequestBody DepartamentoTecnico departamentosTecnicos) {
        final DepartamentoTecnico departamentosTecnicosSaved = this.departamentoTecnicoService.cadastraDepartamentoTecnico(
                departamentosTecnicos);
        HttpStatus status = (null == departamentosTecnicosSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<DepartamentoTecnico>(departamentosTecnicosSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getDepartamentoTecnicos() {
        final List<DepartamentoTecnico> departamentosTecnicoss = this.departamentoTecnicoService.getDepartamentosTecnicos();
        HttpStatus status = (null == departamentosTecnicoss) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentosTecnicoss, status);
    }


}
