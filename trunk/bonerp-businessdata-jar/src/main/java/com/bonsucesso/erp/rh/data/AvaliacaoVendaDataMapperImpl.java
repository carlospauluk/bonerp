package com.bonsucesso.erp.rh.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.rh.model.AvaliacaoVenda;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade AvaliacaoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("avaliacaoVendaDataMapper")
public class AvaliacaoVendaDataMapperImpl extends DataMapperImpl<AvaliacaoVenda> implements AvaliacaoVendaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 2542780976170431902L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public AvaliacaoVenda beforeSave(AvaliacaoVenda avaliacaoVenda) throws ViewException {
		getEntityIdHandler().handleEntityIdFilhos(avaliacaoVenda.getQuestoes());
		return avaliacaoVenda;
	}
	
	

}
