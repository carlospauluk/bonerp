package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Banco;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Banco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("bancoFilterFinder")
public class BancoFilterFinderImpl extends FilterEntityFinderImpl<Banco> implements
		BancoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 4277213727836054596L;

}
