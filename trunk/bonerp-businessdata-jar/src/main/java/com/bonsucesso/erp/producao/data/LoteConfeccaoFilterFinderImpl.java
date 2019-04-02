package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade LoteConfeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("loteConfeccaoFilterFinder")
public class LoteConfeccaoFilterFinderImpl extends FilterEntityFinderImpl<LoteConfeccao> implements
		LoteConfeccaoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 6766284508606990062L;

}
