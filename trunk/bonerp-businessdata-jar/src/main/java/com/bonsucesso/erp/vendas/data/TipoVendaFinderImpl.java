package com.bonsucesso.erp.vendas.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.vendas.model.TipoVenda;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade TipoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("tipoVendaFinder")
public class TipoVendaFinderImpl extends BasicEntityFinderImpl<TipoVenda> implements TipoVendaFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1733167712434525554L;

}
