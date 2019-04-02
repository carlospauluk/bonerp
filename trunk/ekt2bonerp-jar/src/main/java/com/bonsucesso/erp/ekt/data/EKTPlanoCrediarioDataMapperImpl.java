package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTPlanoCrediario;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTPlanoCrediario.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektPlanoCrediarioDataMapper")
public class EKTPlanoCrediarioDataMapperImpl extends DataMapperImpl<EKTPlanoCrediario>
		implements EKTPlanoCrediarioDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 901214099757297837L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_plano_crediario").executeUpdate();
	}

}
