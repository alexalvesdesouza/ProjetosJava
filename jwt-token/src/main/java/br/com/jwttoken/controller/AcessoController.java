package br.com.jwttoken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcessoController {
  
  @GetMapping("/")
  public String home() {
    return "Home Page --> teste";
  }
//  
//  @PostMapping("/login")
//  public String login(@RequestBody UserCredentials user) {
//    System.out.println(user);
//    return "Home Page";
//  }

}
