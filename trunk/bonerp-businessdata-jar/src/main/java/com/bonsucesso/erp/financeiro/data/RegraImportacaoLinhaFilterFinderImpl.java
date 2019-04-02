package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.RegraImportacaoLinha;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade RegraImportacaoLinha.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("regraImportacaoLinhaFilterFinder")
public class RegraImportacaoLinhaFilterFinderImpl extends FilterEntityFinderImpl<RegraImportacaoLinha> implements
		RegraImportacaoLinhaFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -6084145904802141727L;

}
