package com.bonsucesso.erp.vendas.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("vendaFilterFinder")
public class VendaFilterFinderImpl extends FilterEntityFinderImpl<Venda> implements
		VendaFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 3773594123838549808L;

}
