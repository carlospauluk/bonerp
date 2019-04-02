package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade CentroCusto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("centroCustoFinder")
public class CentroCustoFinderImpl extends BasicEntityFinderImpl<CentroCusto>
		implements CentroCustoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -5831757300284767881L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public CentroCusto findBy(Integer codigo) throws ViewException {
		final String jpql = "FROM CentroCusto WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);

	}

}
