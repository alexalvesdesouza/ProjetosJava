package br.com.lufamador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Status não permitido para Operação!") 
public class BussinessForbbidenException  extends RuntimeException{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

}
