package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Grupo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade Grupo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("grupoFinder")
public class GrupoFinderImpl extends BasicEntityFinderImpl<Grupo> implements
		GrupoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 3463758287754127365L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Grupo> findAtivos() throws ViewException {
		final String jpql = "FROM Grupo WHERE ativo = true ORDER BY descricao";
		return getThiz().findResults(jpql, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Grupo findBy(String descricao) throws ViewException {
		final String jpql = "FROM Grupo WHERE descricao LIKE :descricao";
		return getThiz().findSingleResult(jpql, "descricao", descricao);
	}

}
