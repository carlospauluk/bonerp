package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade GrupoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("grupoItemDataMapper")
public class GrupoItemDataMapperImpl extends DataMapperImpl<GrupoItem> implements GrupoItemDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 2789374318666212252L;

}
