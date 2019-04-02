package com.bonsucesso.erp.cortinas.data;



import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.cortinas.model.Cortina;
import com.ocabit.base.data.DataMapperImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação Data Mapper para a entidade Cortina.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("cortinaDataMapper")
public class CortinaDataMapperImpl extends DataMapperImpl<Cortina> implements CortinaDataMapper {

	/**
	 *
	 */
	private static final long serialVersionUID = 3889935365566809536L;

	@Autowired
	private BeanFactory beanFactory;

	@Override
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED)
	public Cortina beforeSave(Cortina cortina) throws ViewException {
		getEntityIdHandler().handleEntityId(cortina.getOrcamentoItem());
		getEntityIdHandler().handleEntityIdFilhos(cortina.getItens());
		getEntityIdHandler().handleEntityIdFilhos(cortina.getLargurasLados());
		return cortina;
	}
	
	

}
