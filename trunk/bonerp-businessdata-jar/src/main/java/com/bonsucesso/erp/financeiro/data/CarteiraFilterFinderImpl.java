package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Carteira.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("carteiraFilterFinder")
public class CarteiraFilterFinderImpl extends FilterEntityFinderImpl<Carteira> implements
		CarteiraFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -8792173215162275015L;

}
