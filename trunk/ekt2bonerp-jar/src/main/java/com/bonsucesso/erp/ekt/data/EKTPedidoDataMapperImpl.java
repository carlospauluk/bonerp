package com.bonsucesso.erp.ekt.data;



import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTPedido;
import com.ocabit.base.data.DataMapperImpl;


/**
 * Implementação DataMapper para a entidade EKTPedido.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("ektPedidoDataMapper")
public class EKTPedidoDataMapperImpl extends DataMapperImpl<EKTPedido> implements EKTPedidoDataMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1235704284855021000L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void truncateTable() {
		getEntityManager().createNativeQuery("TRUNCATE TABLE ekt_pedido").executeUpdate();
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public void deleteEmissaoMaiorQue(Date emissao) {
		Query qry = getEntityManager().createNativeQuery("DELETE FROM ekt_pedido WHERE EMISSAO > :emissao");
		qry.setParameter("emissao", emissao);
		qry.executeUpdate();
	}
}
