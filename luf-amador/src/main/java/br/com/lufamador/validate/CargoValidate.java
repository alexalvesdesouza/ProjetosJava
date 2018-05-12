package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.model.Cargo;
import br.com.lufamador.repository.CargoRepository;

@Component
public class CargoValidate {

    private final CargoRepository repository;

    @Autowired
    public CargoValidate(CargoRepository repository) {
        this.repository = repository;
    }

    public void validaCargoExistente(final Cargo cargo) {
//        final Cargo user = this.repository.findByLogin(cargo.getLogin());
//        if (user != null && user.getAtivo())
//            throw new ValidateException(MensagensErro.USUARIO_DUPLICADO);

    }
}
