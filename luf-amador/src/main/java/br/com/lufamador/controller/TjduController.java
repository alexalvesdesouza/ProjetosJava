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

import br.com.lufamador.model.Tjdu;
import br.com.lufamador.service.impl.TjduService;
import br.com.lufamador.utils.constants.CategoriaConstant;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/tjdus")
public class TjduController {

    private TjduService tjduService;

    @Autowired
    public TjduController(TjduService tjduService) {
        this.tjduService = tjduService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Tjdu> cadastraTjdu(@RequestBody Tjdu tjdu) {
        final Tjdu tjduSaved = this.tjduService.cadastraTjdu(tjdu);
        HttpStatus status = (null == tjduSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<>(tjduSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Tjdu> editarTjdu(@RequestBody Tjdu tjdu) {
        final Tjdu tjduSaved = this.tjduService.editarTjdu(tjdu);
        HttpStatus status = (null == tjduSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;
        return new ResponseEntity<>(tjduSaved,
                status);
    }

    @RequestMapping(path = "/{codigo}/", method = RequestMethod.DELETE)
    public ResponseEntity<Tjdu> deletarTjdu(@PathVariable(value = "codigo") Long codigo) {
        this.tjduService.deletarTjdu(codigo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "/editais", method = RequestMethod.GET)
    public ResponseEntity<List<Tjdu>> getEditaisTjdus() {
        final List<Tjdu> tjdus = this.tjduService.getTjduList(CategoriaConstant.EDITAIS.name());
        HttpStatus status = (null == tjdus) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(tjdus, status);
    }

    @RequestMapping(path = "/portarias", method = RequestMethod.GET)
    public ResponseEntity<List<Tjdu>> getPortatiasTjdus() {
        final List<Tjdu> tjdus = this.tjduService.getTjduList(CategoriaConstant.PORTARIAS.name());
        HttpStatus status = (null == tjdus) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(tjdus, status);
    }

    @RequestMapping(path = "/resultados", method = RequestMethod.GET)
    public ResponseEntity<List<Tjdu>> getTjdus() {
        final List<Tjdu> tjdus = this.tjduService.getTjduList(CategoriaConstant.RESULTADOS.name());
        HttpStatus status = (null == tjdus) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(tjdus, status);
    }

}
