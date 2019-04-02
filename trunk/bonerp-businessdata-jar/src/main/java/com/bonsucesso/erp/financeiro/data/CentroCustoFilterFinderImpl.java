package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade CentroCusto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("centroCustoFilterFinder")
public class CentroCustoFilterFinderImpl extends FilterEntityFinderImpl<CentroCusto> implements
		CentroCustoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -5442622008712408502L;

}
