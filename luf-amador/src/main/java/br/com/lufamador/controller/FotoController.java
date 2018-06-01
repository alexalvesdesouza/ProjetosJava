package br.com.lufamador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.lufamador.service.Disco;

@RestController
@RequestMapping(path = "/fotos")
public class FotoController {

    private final Disco disco;

    @Autowired
    public FotoController(Disco disco) {
        this.disco = disco;
    }

    @PostMapping
    public void upload(@RequestParam MultipartFile foto) {
        this.disco.salvarFoto(foto);
    }

}
