package com.bonsucesso.erp.rh.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.model.AvaliacaoVenda;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade AvaliacaoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("avaliacaoVendaFilterFinder")
public class AvaliacaoVendaFilterFinderImpl extends FilterEntityFinderImpl<AvaliacaoVenda> implements
		AvaliacaoVendaFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 8858909166050025424L;

}
