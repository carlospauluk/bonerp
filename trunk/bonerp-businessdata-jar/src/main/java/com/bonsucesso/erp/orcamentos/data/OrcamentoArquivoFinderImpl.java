package com.bonsucesso.erp.orcamentos.data;



import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.orcamentos.model.Orcamento;
import com.bonsucesso.erp.orcamentos.model.OrcamentoArquivo;
import com.ocabit.base.data.BasicEntityFinderImpl;


/**
 * Implementação de Finder para entidade OrcamentoArquivo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("orcamentoArquivoFinder")
public class OrcamentoArquivoFinderImpl extends BasicEntityFinderImpl<OrcamentoArquivo> implements
		OrcamentoArquivoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -2730198315452033719L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<OrcamentoArquivo> findBy(Orcamento orcamento) {
		final String jpql = "FROM OrcamentoArquivo WHERE orcamento = :orcamento";
		final Query qry = getEntityManager().createQuery(jpql);
		qry.setParameter("orcamento", orcamento);
		return qry.getResultList();
	}

}
