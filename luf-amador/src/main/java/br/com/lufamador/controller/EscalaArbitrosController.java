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

import br.com.lufamador.model.EscalaArbitros;
import br.com.lufamador.service.impl.EscalaArbitrosService;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/escalas")
public class EscalaArbitrosController {

    private EscalaArbitrosService escalaArbitrosService;

    @Autowired
    public EscalaArbitrosController(EscalaArbitrosService escalaArbitrosService) {
        this.escalaArbitrosService = escalaArbitrosService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EscalaArbitros> cadastraEscalaArbitros(@RequestBody EscalaArbitros escalaArbitros) {
        final EscalaArbitros escalaArbitrosSaved = this.escalaArbitrosService.cadastraEscalaArbitros(escalaArbitros);
        HttpStatus status = (null == escalaArbitrosSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<>(escalaArbitrosSaved,

                status);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<EscalaArbitros> atualizarEscalaArbitros(@RequestBody EscalaArbitros escalaArbitros) {
        final EscalaArbitros escalaArbitrosSaved = this.escalaArbitrosService.atualizarEscalaArbitros(escalaArbitros);
        HttpStatus status = (null == escalaArbitrosSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<>(escalaArbitrosSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EscalaArbitros>> getEscalaArbitross() {
        final List<EscalaArbitros> escalaArbitross = this.escalaArbitrosService.getEscalaArbitrosList();
        HttpStatus status = (null == escalaArbitross) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(escalaArbitross, status);
    }

    @RequestMapping(path = "/{codigo}/", method = RequestMethod.DELETE)
    public ResponseEntity deletaInscricao(@PathVariable("codigo") Long codigo) {
        this.escalaArbitrosService.excluirEscala(codigo);
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

}
