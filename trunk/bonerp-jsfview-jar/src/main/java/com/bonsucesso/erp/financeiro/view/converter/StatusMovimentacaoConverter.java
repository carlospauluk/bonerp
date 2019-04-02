package com.bonsucesso.erp.financeiro.view.converter;



import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Service;

import com.bonsucesso.erp.financeiro.model.Status;


@FacesConverter("statusMovimentacaoConverter")
@Service("statusMovimentacaoConverter")
public class StatusMovimentacaoConverter extends EnumConverter {

	public StatusMovimentacaoConverter() {
		super(Status.class);
	}

}
