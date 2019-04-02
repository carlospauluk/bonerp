package com.bonsucesso.erp.estoque.results.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.estoque.results.model.HistoricoPrevisaoCompra;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("historicoPrevisaoCompraFinder")
public class HistoricoPrevisaoCompraFinderImpl extends BasicEntityFinderImpl<HistoricoPrevisaoCompra>
		implements HistoricoPrevisaoCompraFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6222207862600295871L;

}
