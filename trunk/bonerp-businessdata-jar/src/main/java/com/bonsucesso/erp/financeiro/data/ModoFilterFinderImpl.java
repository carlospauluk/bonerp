package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Modo;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Modo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("modoFilterFinder")
public class ModoFilterFinderImpl extends FilterEntityFinderImpl<Modo> implements
		ModoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 5383779337757341440L;

}
