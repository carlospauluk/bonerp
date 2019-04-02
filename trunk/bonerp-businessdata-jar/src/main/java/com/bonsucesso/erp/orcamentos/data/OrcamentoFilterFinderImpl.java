package com.bonsucesso.erp.orcamentos.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Orcamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("orcamentoFilterFinder")
public class OrcamentoFilterFinderImpl extends FilterEntityFinderImpl<Orcamento> implements OrcamentoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 2929306953110346695L;

}
