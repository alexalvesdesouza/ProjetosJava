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
import org.springframework.web.bind.annotation.RestController;

import br.com.lufamador.model.Cargo;
import br.com.lufamador.service.CargoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cargos")
public class CargoController {

    private CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Cargo> cadastraCargo(@RequestBody Cargo cargo) {
        final Cargo cargoSaved = this.cargoService.cadastraCargo(cargo);
        HttpStatus status = (null == cargoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;
        return new ResponseEntity<Cargo>(cargoSaved,
                status);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cargo>> getCargos() {
        final List<Cargo> cargos = this.cargoService.getCargos();
        HttpStatus status = (null == cargos) ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(cargos, status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Cargo> atualizaCargo(@RequestBody Cargo cargo) {
        final Cargo cargoSuspenso = this.cargoService.atulizarCargo(cargo);
        HttpStatus status = (null == cargoSuspenso) ? HttpStatus.UNPROCESSABLE_ENTITY : HttpStatus.OK;
        return new ResponseEntity<Cargo>(cargoSuspenso, status);
    }


}
