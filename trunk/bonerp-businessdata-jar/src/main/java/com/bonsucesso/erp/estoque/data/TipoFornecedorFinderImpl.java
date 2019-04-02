package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.model.TipoFornecedor;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade TipoFornecedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("tipoFornecedorFinder")
public class TipoFornecedorFinderImpl extends BasicEntityFinderImpl<TipoFornecedor> implements TipoFornecedorFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -3910408041548608212L;

}
