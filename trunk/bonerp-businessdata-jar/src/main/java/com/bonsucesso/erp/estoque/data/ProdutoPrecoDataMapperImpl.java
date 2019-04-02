package com.bonsucesso.erp.estoque.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade ProdutoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("produtoPrecoDataMapper")
public class ProdutoPrecoDataMapperImpl extends DataMapperImpl<ProdutoPreco> implements
		ProdutoPrecoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 6152227534870782979L;

}
