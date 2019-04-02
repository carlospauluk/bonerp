package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTSubDepto;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTSubDepto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektSubDeptoDataMapper")
public class EKTSubDeptoDataMapperImpl extends DataMapperImpl<EKTSubDepto> implements EKTSubDeptoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 111301351153737783L;
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_subdepto").executeUpdate();
	}

}
