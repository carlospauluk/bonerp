package com.bonsucesso.erp.vendas.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade PlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("planoPagtoFinder")
public class PlanoPagtoFinderImpl extends BasicEntityFinderImpl<PlanoPagto> implements PlanoPagtoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 6133307021699241154L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public PlanoPagto findBy(String codigo) throws ViewException {
		String jpql = "FROM PlanoPagto WHERE codigo LIKE :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public PlanoPagto findByDescricao(String descricao) throws ViewException {
		String jpql = "FROM PlanoPagto WHERE descricao LIKE :descricao";
		return getThiz().findSingleResult(jpql, "descricao", descricao);
	}

}
