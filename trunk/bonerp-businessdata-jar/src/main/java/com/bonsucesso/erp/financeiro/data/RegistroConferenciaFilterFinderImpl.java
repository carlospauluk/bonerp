package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.financeiro.model.RegistroConferencia;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade RegistroConferencia.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("registroConferenciaFilterFinder")
public class RegistroConferenciaFilterFinderImpl extends FilterEntityFinderImpl<RegistroConferencia> implements
		RegistroConferenciaFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -2123166760201326505L;

}
