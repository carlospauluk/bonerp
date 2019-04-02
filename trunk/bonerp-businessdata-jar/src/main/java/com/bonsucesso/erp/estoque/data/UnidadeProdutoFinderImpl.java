package com.bonsucesso.erp.estoque.data;



import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.UnidadeProduto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade UnidadeProduto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("unidadeProdutoFinder")
public class UnidadeProdutoFinderImpl extends BasicEntityFinderImpl<UnidadeProduto> implements UnidadeProdutoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 6474766091349943521L;
	protected static Logger logger = Logger.getLogger(UnidadeProdutoFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public UnidadeProduto findByLabel(String label) throws ViewException {
		final String jpql = "FROM UnidadeProduto WHERE label LIKE :label";
		return getThiz().findSingleResult(jpql, "label", label);
	}
}
