package br.com.lufamador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
                CategoriaConstant.EDITAIS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/comunicados", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getComunicadosDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.COMUNICADOS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/notas-oficiais", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getNotasOficiaisDepartamentoTecnico() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.NOTAS_OFICIAIS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/sumulas", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getSumulasDepartamentoTecnicos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.SUMULAS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/artilharia-defesa", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getArtilhariasCampeonatos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.ARTILHARIA_DEFESA.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }

    @RequestMapping(path = "/tabelas-jogos", method = RequestMethod.GET)
    public ResponseEntity<List<DepartamentoTecnico>> getTabelasJogos() {
        final List<DepartamentoTecnico> departamentoTecnicos = this.departamentoTecnicoServiceImpl.getDepartamentoTecnicoList(
                CategoriaConstant.TABELA_JOGOS.name());
        HttpStatus status = (null == departamentoTecnicos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(departamentoTecnicos, status);
    }


}
