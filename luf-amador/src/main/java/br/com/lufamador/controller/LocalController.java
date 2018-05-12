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

import br.com.lufamador.model.Local;
import br.com.lufamador.model.Local;
import br.com.lufamador.service.LocalService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/locais")
public class LocalController {

    private LocalService localService;

    @Autowired
    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Local> cadastraLocal(@RequestBody Local local) {
        final Local localSaved = this.localService.cadastraLocal(local);
        HttpStatus status = (null == localSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<Local>(localSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Local>> getLocals() {
        final List<Local> locals = this.localService.getLocais();
        HttpStatus status = (null == locals) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(locals, status);
    }


}
