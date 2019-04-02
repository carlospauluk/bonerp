package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.model.DepreciacaoPreco;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("depreciacaoPrecoDataMapper")
public class DepreciacaoPrecoDataMapperImpl extends DataMapperImpl<DepreciacaoPreco> implements
		DepreciacaoPrecoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 6064476337030865458L;

}
