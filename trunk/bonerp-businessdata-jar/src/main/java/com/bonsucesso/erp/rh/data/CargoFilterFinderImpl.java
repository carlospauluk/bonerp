package com.bonsucesso.erp.rh.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.model.Cargo;
import com.ocabit.base.data.FilterEntityFinderImpl;


/**
 * Implementação de FilterFinder para entidade Cargo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("cargoFilterFinder")
public class CargoFilterFinderImpl extends FilterEntityFinderImpl<Cargo> implements CargoFilterFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 6422669755024990944L;

}
