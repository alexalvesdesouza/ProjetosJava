package br.com.oauth2.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.oauth2.br.com.oauth2.model.Cliente;

@RestController(value = "clientes")
public class ClienteController {

    @GetMapping
    public List<Cliente> getClientes() {
        Cliente c1 = new Cliente(1, "Alex Alves");
        Cliente c2 = new Cliente(2, "Karla Morena");
        Cliente c3 = new Cliente(3, "Juliana Silva");
        return Arrays.asList(c1,c2,c3);
    }
}
