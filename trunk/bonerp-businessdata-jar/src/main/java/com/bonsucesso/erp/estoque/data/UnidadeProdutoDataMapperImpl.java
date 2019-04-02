package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.model.UnidadeProduto;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade UnidadeProduto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("unidadeProdutoDataMapper")
public class UnidadeProdutoDataMapperImpl extends DataMapperImpl<UnidadeProduto> implements
		UnidadeProdutoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -3571005231355189125L;

}
