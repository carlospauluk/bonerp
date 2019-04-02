package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTPlanoPagto;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTPlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektPlanoPagtoDataMapper")
public class EKTPlanoPagtoDataMapperImpl extends DataMapperImpl<EKTPlanoPagto>
		implements EKTPlanoPagtoDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4905875389692002117L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_plano_pagto").executeUpdate();
	}

}
