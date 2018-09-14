package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.lufamador.model.Arbitro;
import br.com.lufamador.service.impl.ArbitrosServiceImpl;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/arbitros")
public class ArbitrosController {

    @Autowired
    private ArbitrosServiceImpl arbitroService;

    @GetMapping
    public ResponseEntity<List<Arbitro>> getEscalas() {
        List<Arbitro> list = this.arbitroService.getArbitros();
        return ResponseEntity.ok(list);
    }

}
