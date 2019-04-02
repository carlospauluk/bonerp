package com.bonsucesso.erp.financeiro.data;



import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade OperadoraCartao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("operadoraCartaoFinder")
public class OperadoraCartaoFinderImpl extends BasicEntityFinderImpl<OperadoraCartao> implements
		OperadoraCartaoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 2654976741629833172L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public OperadoraCartao findBy(String descricao) throws ViewException {
		return getThiz()
				.findSingleResult("FROM OperadoraCartao WHERE descricao LIKE :descricao", "descricao", descricao);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public OperadoraCartao findBy(Carteira carteira) throws ViewException {
		return getThiz()
				.findSingleResult("FROM OperadoraCartao WHERE carteira LIKE :carteira", "carteira", carteira);
	}

}
