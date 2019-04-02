package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTVenda;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektVendaDataMapper")
public class EKTVendaDataMapperImpl extends DataMapperImpl<EKTVenda> implements EKTVendaDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3893458170744141998L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_venda").executeUpdate();
	}

}
