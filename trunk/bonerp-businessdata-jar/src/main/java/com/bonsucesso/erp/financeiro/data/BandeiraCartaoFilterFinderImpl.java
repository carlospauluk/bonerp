package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade BandeiraCartao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("bandeiraCartaoFilterFinder")
public class BandeiraCartaoFilterFinderImpl extends FilterEntityFinderImpl<BandeiraCartao> implements
		BandeiraCartaoFilterFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6059949343184729859L;

}
