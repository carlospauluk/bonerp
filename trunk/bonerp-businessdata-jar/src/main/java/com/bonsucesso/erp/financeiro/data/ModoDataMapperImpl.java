package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.financeiro.model.Modo;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade Modo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("modoDataMapper")
public class ModoDataMapperImpl extends DataMapperImpl<Modo> implements ModoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -6474353488558464513L;

}
