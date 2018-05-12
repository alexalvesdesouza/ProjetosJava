package br.com.lufamador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidateException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ValidateException() {
    super();
  }

  public ValidateException(String message, Throwable cause) {
    super(message, cause);
  }

  public ValidateException(String message) {
    super(message);
  }

  public ValidateException(Throwable cause) {
    super(cause);
  }

}
