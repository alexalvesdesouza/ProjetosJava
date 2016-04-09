package br.com.pgi.model.util;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.pgi.model.entities.Operadordosistema;


@SuppressWarnings("serial")
public class ConverterOperador implements Converter, Serializable {

	// Converte da tela para o Objeto
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.equals("Selecione um Operador")) {
			return null;
		}
		return JPAUtil.getEntityManager().find(Operadordosistema.class,
				Integer.parseInt(string));
	}

	// Conberte do Objeto para a Tela
	// @SuppressWarnings("static-access")
	public String getAsString(FacesContext arg0, UIComponent arg1, Object o) {
		if (o == null) {
			return null;
		}
		Operadordosistema obj = (Operadordosistema) o;
		String ret = Integer.toString(obj.getIdOperador());

		return ret;
	}

}
