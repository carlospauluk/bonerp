package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTEncomenda;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTEncomenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektEncomendaDataMapper")
public class EKTEncomendaDataMapperImpl extends DataMapperImpl<EKTEncomenda> implements EKTEncomendaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 5443049193706441531L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_encomenda").executeUpdate();
	}

}
