package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.model.Instituicao;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Instituicao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("instituicaoFilterFinder")
public class InstituicaoFilterFinderImpl extends FilterEntityFinderImpl<Instituicao> implements
		InstituicaoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 1170131093863359704L;

}
