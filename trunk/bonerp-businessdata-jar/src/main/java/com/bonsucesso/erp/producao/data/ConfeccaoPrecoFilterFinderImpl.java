package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de Finder para entidade ConfeccaoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("confeccaoPrecoFilterFinder")
public class ConfeccaoPrecoFilterFinderImpl extends FilterEntityFinderImpl<ConfeccaoPreco> implements
		ConfeccaoPrecoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -5573447432372284046L;

}
