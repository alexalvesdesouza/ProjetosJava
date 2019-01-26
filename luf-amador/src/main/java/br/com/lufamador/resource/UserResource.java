package br.com.lufamador.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
//@CrossOrigin(origins = "*")
public class UserResource {

    int count = 0;
    @GetMapping
    public String yes() {

        System.out.println(1+1);
        return "YES";
    }
}
