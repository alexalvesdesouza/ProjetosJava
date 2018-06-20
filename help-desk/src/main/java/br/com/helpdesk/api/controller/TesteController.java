package br.com.helpdesk.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {

  @GetMapping(value = "/teste")
  public String getTeste() {
    return "Merda !!!";
  }
}
