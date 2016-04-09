package br.com.pgi.model.util;

public class UtilErros {

	public static String getMensagemErro(Exception e) {
		while (e.getCause() != null) {
			e = (Exception) e.getCause();

		}
		String msgRetorno = e.getMessage();
		return msgRetorno;
	}
}
