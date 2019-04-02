package com.bonsucesso.erp.vendas.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.bonsucesso.erp.vendas.model.VendaItem;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação de DataMapper para a entidade VendaItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("vendaItemDataMapper")
public class VendaItemDataMapperImpl extends DataMapperImpl<VendaItem> implements VendaItemDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6767448853468012161L;

	protected static Logger logger = Logger.getLogger(VendaItemDataMapperImpl.class);

}
