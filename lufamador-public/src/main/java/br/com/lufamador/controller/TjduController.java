package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.lufamador.model.Tjdu;
import br.com.lufamador.service.impl.TjduServiceImpl;
import br.com.lufamador.utils.constants.CategoriaConstant;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/tjdus")
public class TjduController {

    @Autowired
    private TjduServiceImpl tjduService;

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
