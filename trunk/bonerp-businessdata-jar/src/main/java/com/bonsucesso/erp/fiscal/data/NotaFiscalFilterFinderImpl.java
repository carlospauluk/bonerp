package com.bonsucesso.erp.fiscal.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade NotaFiscal.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("notaFiscalFilterFinder")
public class NotaFiscalFilterFinderImpl extends FilterEntityFinderImpl<NotaFiscal>
		implements NotaFiscalFilterFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5091484943176258551L;

}
