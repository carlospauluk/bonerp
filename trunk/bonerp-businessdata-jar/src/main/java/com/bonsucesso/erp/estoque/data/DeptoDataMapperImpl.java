package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.model.Departamento;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Datatables.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("deptoDataMapper")
public class DeptoDataMapperImpl extends DataMapperImpl<Departamento> implements DeptoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -7390680871938550534L;

	@Override
	public Departamento beforeSave(Departamento depto) throws ViewException {
		getEntityIdHandler().handleEntityIdFilhos(depto.getSubdeptos());
		return depto;
	}
	
	

}
