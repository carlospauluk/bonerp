package com.bonsucesso.erp.financeiro.view.converter;



import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Service;

import com.bonsucesso.erp.financeiro.model.TipoLancto;


@FacesConverter("tipoLanctoConverter")
@Service("tipoLanctoConverter")
public class TipoLanctoConverter extends EnumConverter {

	public TipoLanctoConverter() {
		super(TipoLancto.class);
	}

}
