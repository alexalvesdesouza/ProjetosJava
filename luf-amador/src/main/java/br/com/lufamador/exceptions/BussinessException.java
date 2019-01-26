package br.com.lufamador.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BussinessException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public BussinessException() {
    super();
  }

  public BussinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BussinessException(String message) {
    super(message);
  }

  public BussinessException(Throwable cause) {
    super(cause);
  }

}
