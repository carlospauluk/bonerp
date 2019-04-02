package com.bonsucesso.erp.crm.view.converter;



import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

import org.springframework.stereotype.Service;

import com.bonsucesso.erp.crm.model.StatusCupom;


@FacesConverter("statusCupomConverter")
@Service("statusCupomConverter")
public class StatusCupomConverter extends EnumConverter {

	public StatusCupomConverter() {
		super(StatusCupom.class);
	}

}
