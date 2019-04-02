package com.bonsucesso.erp.ekt.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTProduto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektProdutoDataMapper")
public class EKTProdutoDataMapperImpl extends DataMapperImpl<EKTProduto> implements EKTProdutoDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -93962556218597038L;
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_produto").executeUpdate();
		getEntityManager().flush();
	}

}
