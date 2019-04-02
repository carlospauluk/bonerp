package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.producao.model.Insumo;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Insumo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("insumoFilterFinder")
public class InsumoFilterFinderImpl extends FilterEntityFinderImpl<Insumo> implements InsumoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -6042873791464271990L;

}
