package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade ConfeccaoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("confeccaoPrecoDataMapper")
public class ConfeccaoPrecoDataMapperImpl extends DataMapperImpl<ConfeccaoPreco> implements ConfeccaoPrecoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 375068734774176187L;

}
