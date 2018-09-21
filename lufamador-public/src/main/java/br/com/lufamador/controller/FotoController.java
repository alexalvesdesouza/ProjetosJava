package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Foto;
import br.com.lufamador.service.impl.FotoServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/fotos")
public class FotoController {

    @Autowired
    private FotoServiceImpl fotoService;

    @GetMapping
    public ResponseEntity<List<Foto>> getFotos() {
        List<Foto> list = this.fotoService.findAll();
        return ResponseEntity.ok(list);
    }
}
