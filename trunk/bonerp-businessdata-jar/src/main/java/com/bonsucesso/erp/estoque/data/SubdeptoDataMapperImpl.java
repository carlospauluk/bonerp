package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade Datatables.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("subdeptoDataMapper")
public class SubdeptoDataMapperImpl extends DataMapperImpl<Subdepartamento> implements SubdeptoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -7121729192832219008L;

}
