package com.bonsucesso.erp.rh.data;



import org.springframework.stereotype.Component;

import com.bonsucesso.erp.rh.model.Cargo;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade Cargo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("cargoFinder")
public class CargoFinderImpl extends BasicEntityFinderImpl<Cargo> implements CargoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -4524722399981372130L;

}
