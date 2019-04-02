package com.bonsucesso.erp.ekt.data;



import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTFornecedor;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTFornecedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektFornecedorDataMapper")
public class EKTFornecedorDataMapperImpl extends DataMapperImpl<EKTFornecedor> implements EKTFornecedorDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -6833898839205903672L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_fornecedor").executeUpdate();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deleteByMesano(String mesano) {
		Query qry = getEntityManager().createQuery("DELETE FROM EKTFornecedor WHERE mesano = :mesano");
		qry.setParameter("mesano", mesano);
		qry.executeUpdate();
	}

}
