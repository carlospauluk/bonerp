package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Carteira.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("carteiraFinder")
public class CarteiraFinderImpl extends BasicEntityFinderImpl<Carteira>
		implements CarteiraFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -413365932395124304L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Carteira> findAllConcretas() throws ViewException {
		final String jpql = "FROM Carteira c WHERE c.concreta = true ORDER BY c.codigo";
		return getThiz().findResults(jpql, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Carteira> findAllCheque() throws ViewException {
		final String jpql = "FROM Carteira c WHERE c.cheque = true ORDER BY c.codigo";
		return getThiz().findResults(jpql, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Carteira> findAllCaixas() throws ViewException {
		final String jpql = "FROM Carteira c WHERE c.caixa = true ORDER BY c.codigo";
		return getThiz().findResults(jpql, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Carteira> findAllAbertas() throws ViewException {
		final String jpql = "FROM Carteira c WHERE c.abertas = true ORDER BY c.codigo";
		return getThiz().findResults(jpql, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Carteira> findAllBancos() throws ViewException {
		final String jpql = "FROM Carteira c WHERE c.banco IS NOT NULL ORDER BY c.codigo";
		return getThiz().findResults(jpql, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Carteira findBy(String descricao) throws ViewException {
		final String jpql = "FROM Carteira WHERE descricao = :descricao";
		return getThiz().findSingleResult(jpql, "descricao", descricao);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Carteira findBy(Integer codigo) throws ViewException {
		final String jpql = "FROM Carteira WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

}
