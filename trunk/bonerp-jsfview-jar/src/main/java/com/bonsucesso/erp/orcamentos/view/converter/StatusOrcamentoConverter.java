package com.bonsucesso.erp.orcamentos.view.converter;



import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Service;

import com.bonsucesso.erp.orcamentos.model.StatusOrcamento;


@FacesConverter("statusOrcamentoConverter")
@Service("statusOrcamentoConverter")
public class StatusOrcamentoConverter extends EnumConverter {

	public StatusOrcamentoConverter() {
		super(StatusOrcamento.class);
	}

}
