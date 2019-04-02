package com.bonsucesso.erp.rh.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.model.AvaliacaoVendaResp;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade AvaliacaoVendaResp.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("avaliacaoVendaRespFilterFinder")
public class AvaliacaoVendaRespFilterFinderImpl extends FilterEntityFinderImpl<AvaliacaoVendaResp> implements
		AvaliacaoVendaRespFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -7444190501161597619L;

}
