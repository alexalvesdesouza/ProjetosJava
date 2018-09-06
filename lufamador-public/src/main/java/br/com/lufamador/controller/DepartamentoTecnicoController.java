package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.service.impl.DepartamentoTecnicoServiceImpl;
import br.com.lufamador.utils.constants.CategoriaConstant;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/departamento-tecnico")
public class DepartamentoTecnicoController {

    @Autowired
    private DepartamentoTecnicoServiceImpl departamentoTecnicoServiceImpl;

    @GetMapping(path = "/editais")
    public ResponseEntity<List<DepartamentoTecnico>> getEditaisDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.EDITAIS.name(), null);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/comunicados", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getComunicadosDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.COMUNICADOS.name(), null);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/regulamentos", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getRegulamentosDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.REGULAMENTOS.name(), null);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/portarias", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getPortariasDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.PORTARIAS.name(), null);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/notas-oficiais", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getNotasOficiaisDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.NOTAS_OFICIAIS.name(), null);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/sumulas/{subCategoria}", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getSumulasDepartamentoTecnicos(
            @PathVariable(value = "subCategoria") String subCategoria) {

        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.SUMULAS.name(), subCategoria);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/artilharia-defesa/{subCategoria}", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getArtilhariasCampeonatos(
            @PathVariable(value = "subCategoria") String subCategoria) {

        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.ARTILHARIA_DEFESA.name(), subCategoria);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/tabelas-jogos", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getTabelasJogos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.TABELA_JOGOS.name(), null);
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/docs-principal", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getDocsPrincipal() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDocsList();
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

}
