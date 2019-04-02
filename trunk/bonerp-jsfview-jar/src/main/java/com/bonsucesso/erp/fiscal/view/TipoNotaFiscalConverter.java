package com.bonsucesso.erp.fiscal.view;



import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Service;

import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;


@FacesConverter("tipoNotaFiscalConverter")
@Service("tipoNotaFiscalConverter")
public class TipoNotaFiscalConverter extends EnumConverter {

	public TipoNotaFiscalConverter() {
		super(TipoNotaFiscal.class);
	}

}
