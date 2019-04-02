package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Modo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Modo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("modoFinder")
public class ModoFinderImpl extends BasicEntityFinderImpl<Modo> implements
		ModoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 180505416636970549L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Modo findBy(String descricao) throws ViewException {
		final String jpql = "FROM Modo WHERE descricao LIKE :descricao";
		return getThiz().findSingleResult(jpql, "descricao", "%" + descricao + "%");
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Modo findBy(Integer codigo) throws ViewException {
		final String jpql = "FROM Modo WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Modo> findAllTransfPropria() throws ViewException {
		final String jpql = "FROM Modo WHERE modoDeTransfPropria = true ORDER BY codigo";
		return getThiz().findResults(jpql, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Modo> findAllTransfCaixa() throws ViewException {
		final String jpql = "FROM Modo WHERE modoDeTransfCaixa = true ORDER BY codigo";
		return getThiz().findResults(jpql, null);
	}

}
