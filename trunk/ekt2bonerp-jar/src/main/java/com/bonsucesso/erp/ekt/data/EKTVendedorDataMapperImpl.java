package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTVendedor;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTVendedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektVendedorDataMapper")
public class EKTVendedorDataMapperImpl extends DataMapperImpl<EKTVendedor> implements EKTVendedorDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1150357705235605915L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_vendedor").executeUpdate();
	}

}
