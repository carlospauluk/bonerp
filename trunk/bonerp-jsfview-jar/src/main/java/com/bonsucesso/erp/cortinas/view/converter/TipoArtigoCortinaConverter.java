package com.bonsucesso.erp.cortinas.view.converter;



import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Service;

import com.bonsucesso.erp.cortinas.model.TipoArtigoCortina;


@FacesConverter("tipoArtigoCortinaConverter")
@Service("tipoArtigoCortinaConverter")
public class TipoArtigoCortinaConverter extends EnumConverter {

	public TipoArtigoCortinaConverter() {
		super(TipoArtigoCortina.class);
	}

}
