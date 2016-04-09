package br.com.pgi.model.util;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.pgi.model.entities.Promocao;



public class ConverterPromocao implements Converter, Serializable {

	// Converte da tela para o Objeto
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String string) {
		if (string == null || string.equals("Selecione uma Promocao")) {
			return null;
		}
		return JPAUtil.getEntityManager().find(Promocao.class,
				Integer.parseInt(string));
	}

	// Conberte do Objeto para a Tela
	// @SuppressWarnings("static-access")
	public String getAsString(FacesContext arg0, UIComponent arg1, Object o) {
		if (o == null) {
			return null;
		}
		Promocao obj = (Promocao) o;
		String ret = Integer.toString(obj.getIdPromocao());
		//
		// ret = ""+obj.getIdPromocao();
		// ret.valueOf(obj.getIdPromocao());
		// ret = Integer.toString(obj.getIdPromocao());
		return ret;
	}

}
