package com.bonsucesso.erp.estoque.view;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Service;


/**
 * Converter para reduzidos (de long para AAMM-999999)
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@FacesConverter("reduzidoConverter")
@Service("reduzidoConverter")
public class ReduzidoConverter implements Converter {

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) {
		try {
			return Long.parseLong(value.replaceAll("[^\\d.]", ""));
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		return value.toString().substring(0, 4).concat("-").concat(value.toString().substring(4));
	}
}
