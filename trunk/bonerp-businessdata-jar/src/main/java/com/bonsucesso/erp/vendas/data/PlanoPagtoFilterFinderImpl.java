package com.bonsucesso.erp.vendas.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade PlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("planoPagtoFilterFinder")
public class PlanoPagtoFilterFinderImpl extends FilterEntityFinderImpl<PlanoPagto> implements
		PlanoPagtoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 3450998297505340232L;

}
