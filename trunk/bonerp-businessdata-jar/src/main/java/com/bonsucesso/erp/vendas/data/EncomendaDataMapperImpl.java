package com.bonsucesso.erp.vendas.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.vendas.model.Encomenda;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de DataMapper para a entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("encomendaDataMapper")
public class EncomendaDataMapperImpl extends DataMapperImpl<Encomenda> implements EncomendaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = -2563784541986199219L;

	protected static Logger logger = Logger.getLogger(EncomendaDataMapperImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Encomenda beforeSave(Encomenda encomenda) throws ViewException {
		getEntityIdHandler().handleEntityIdFilhos(encomenda.getItens());
		return encomenda;
	}

}
