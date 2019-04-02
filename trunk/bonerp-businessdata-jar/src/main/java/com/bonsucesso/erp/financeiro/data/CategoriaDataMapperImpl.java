package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.Categoria;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade Categoria.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("categoriaDataMapper")
public class CategoriaDataMapperImpl extends DataMapperImpl<Categoria> implements CategoriaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -3034949862629024630L;

}
