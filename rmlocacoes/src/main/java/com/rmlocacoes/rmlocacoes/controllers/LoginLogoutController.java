package com.rmlocacoes.rmlocacoes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginLogoutController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String loginForm() {
    return "login";
  }

  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public String logoutForm() {
    return "logout";
  }

}
