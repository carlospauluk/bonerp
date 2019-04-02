package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.model.MarcacaoMercadoria;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade EntradaMercadoria.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("entradaMercadoriaFilterFinder")
public class EntradaMercadoriaFilterFinderImpl extends FilterEntityFinderImpl<MarcacaoMercadoria>
		implements EntradaMercadoriaFilterFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2532428373517081342L;

}
