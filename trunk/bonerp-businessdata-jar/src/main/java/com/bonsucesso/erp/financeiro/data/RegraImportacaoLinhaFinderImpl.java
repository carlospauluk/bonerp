package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.RegraImportacaoLinha;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade RegraImportacaoLinha.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("regraImportacaoLinhaFinder")
public class RegraImportacaoLinhaFinderImpl extends BasicEntityFinderImpl<RegraImportacaoLinha> implements
		RegraImportacaoLinhaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -3184660379634909987L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<RegraImportacaoLinha> findAllBy(Carteira carteira) throws ViewException {
		final String jpql = "FROM RegraImportacaoLinha WHERE "
				+ "carteira IS NULL OR "
				+ "carteiraDestino IS NULL OR "
				+ "carteiraDestino = :carteira OR "
				+ "carteira = :carteira "
				+ "ORDER BY carteira.id DESC";
		return getThiz().findResults(jpql, "carteira", carteira);
	}
	
}
