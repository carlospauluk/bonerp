package com.bonsucesso.erp.financeiro.view.converter;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bonsucesso.erp.financeiro.model.Categoria;
import com.ocabit.utils.strings.StringUtils;


@FacesConverter("categoriaCodigoConverter")
@Service("categoriaCodigoConverter")
public class CategoriaCodigoConverter implements Converter {

	protected static Logger logger = Logger.getLogger(CategoriaCodigoConverter.class);

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value.replace(".", "");
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		try {
			// O código é Long, portanto o máximo é 9223372036854775807 (9.223.372.036.8547.75807)
			return StringUtils.mascarar((Long) value, Categoria.MASK);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

}
