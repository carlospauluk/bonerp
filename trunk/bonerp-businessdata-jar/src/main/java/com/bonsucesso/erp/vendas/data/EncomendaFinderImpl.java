package com.bonsucesso.erp.vendas.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.vendas.model.Encomenda;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Encomenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("encomendaFinder")
public class EncomendaFinderImpl extends BasicEntityFinderImpl<Encomenda> implements EncomendaFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6986280448283831848L;

	@Override
	public EncomendaFinder getThiz() {
		return (EncomendaFinder) super.getThiz();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Encomenda findBy(Integer numero) throws ViewException {
		return getThiz().findSingleResult("FROM Encomenda WHERE numero = :numero", "numero", numero);
	}

}
