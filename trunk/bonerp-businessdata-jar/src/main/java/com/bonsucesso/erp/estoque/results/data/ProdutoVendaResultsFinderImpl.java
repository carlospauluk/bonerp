package com.bonsucesso.erp.estoque.results.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.results.model.ProdutoVendaResults;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade ProdutoVendaResults.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("produtoVendaResultsFinder")
public class ProdutoVendaResultsFinderImpl extends BasicEntityFinderImpl<ProdutoVendaResults>
		implements ProdutoVendaResultsFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -119136643544906904L;

}
