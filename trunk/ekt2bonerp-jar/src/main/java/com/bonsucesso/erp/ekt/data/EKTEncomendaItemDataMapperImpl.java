package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTEncomendaItem;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTEncomendaItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektEncomendaItemDataMapper")
public class EKTEncomendaItemDataMapperImpl extends DataMapperImpl<EKTEncomendaItem>
		implements EKTEncomendaItemDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 5443049193706441531L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_encomenda_item").executeUpdate();
	}

}
