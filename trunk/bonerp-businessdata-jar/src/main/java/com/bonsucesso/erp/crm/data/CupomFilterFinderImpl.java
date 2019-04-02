package com.bonsucesso.erp.crm.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.crm.model.Cupom;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de Finder para entidade Cupom.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("cupomFilterFinder")
public class CupomFilterFinderImpl extends FilterEntityFinderImpl<Cupom> implements CupomFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 8310152387221851387L;

}
