package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de Finder para entidade Licitacao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("fornecedorFilterFinder")
public class FornecedorFilterFinderImpl extends FilterEntityFinderImpl<Fornecedor> implements FornecedorFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -1404269679057160670L;

}
