package com.bonsucesso.erp.rh.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.rh.model.Cargo;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade Cargo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("cargoDataMapper")
public class CargoDataMapperImpl extends DataMapperImpl<Cargo> implements CargoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 8408021009395895822L;

}
