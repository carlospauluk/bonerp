package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.model.Produto;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("produtoFilterFinder")
public class ProdutoFilterFinderImpl extends FilterEntityFinderImpl<Produto> implements
		ProdutoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 6285155956147886851L;

}
