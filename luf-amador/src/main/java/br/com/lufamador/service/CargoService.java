package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Cargo;
import br.com.lufamador.repository.CargoRepository;
import br.com.lufamador.validate.CargoValidate;

@Service
public class CargoService {

    private final CargoRepository repository;
    private final CargoValidate validate;

    @Autowired
    public CargoService(CargoRepository repository, CargoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Cargo cadastraCargo(Cargo cargo) {
        Cargo cargoSaved = null;
        this.validate.validaCargoExistente(cargo);
        try {
            cargoSaved = this.repository.saveAndFlush(cargo);
        } catch (Exception e) {
            System.out.println(e);
        }
        return cargoSaved;
    }

    public List<Cargo> getCargos() {
        return this.repository.findAll();
    }

    public final Cargo atulizarCargo(Cargo cargo) {
        return this.repository.saveAndFlush(cargo);
    }
}
