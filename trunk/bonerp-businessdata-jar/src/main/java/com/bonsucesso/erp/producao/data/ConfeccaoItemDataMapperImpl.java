package com.bonsucesso.erp.producao.data;



import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação Data Mapper para a entidade ConfeccaoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("confeccaoItemDataMapper")
public class ConfeccaoItemDataMapperImpl extends DataMapperImpl<ConfeccaoItem> implements ConfeccaoItemDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -2169828301298751348L;

}
