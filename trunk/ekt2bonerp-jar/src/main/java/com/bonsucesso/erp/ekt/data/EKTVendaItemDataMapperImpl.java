package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTVendaItem;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTVendaItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektVendaItemDataMapper")
public class EKTVendaItemDataMapperImpl extends DataMapperImpl<EKTVendaItem> implements EKTVendaItemDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2633455445039641109L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_venda_item").executeUpdate();
	}

}
