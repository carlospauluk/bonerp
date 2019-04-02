package com.bonsucesso.erp.fiscal.view;



import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bonsucesso.erp.fiscal.model.TipoNotaFiscal;


/**
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("lmFiscal")
@Scope("view")
public class ListMaker implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TipoNotaFiscal> tipoNotaFiscalValues;

	public List<TipoNotaFiscal> getTipoNotaFiscalValues() {
		this.tipoNotaFiscalValues = Arrays.asList(TipoNotaFiscal.values());
		return this.tipoNotaFiscalValues;
	}

	public void setTipoNotaFiscalValues(List<TipoNotaFiscal> tipoNotaFiscalValues) {
		this.tipoNotaFiscalValues = tipoNotaFiscalValues;
	}

}
