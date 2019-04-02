package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Movimentacao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("movimentacaoFilterFinder")
public class MovimentacaoFilterFinderImpl extends FilterEntityFinderImpl<Movimentacao> implements
		MovimentacaoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 5237161835063462127L;

}
