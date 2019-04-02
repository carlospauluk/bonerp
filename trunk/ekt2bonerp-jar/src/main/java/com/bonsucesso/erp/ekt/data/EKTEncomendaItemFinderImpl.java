package com.bonsucesso.erp.ekt.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTEncomendaItem;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTEncomendaItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektEncomendaItemFinder")
public class EKTEncomendaItemFinderImpl extends BasicEntityFinderImpl<EKTEncomendaItem>
		implements EKTEncomendaItemFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 700478853957577037L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<EKTEncomendaItem> findBy(Double numero) throws ViewException {
		final String jpql = "FROM EKTEncomendaItem WHERE numeroNF = :numero";
		final Query qry = getEntityManager().createQuery(jpql);
		qry.setParameter("numero", numero);
		return qry.getResultList();
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTEncomendaItem findBy(Double numeroNF, Integer recordNumber, Double produto) throws ViewException {
		try {
			final String jpql = "FROM EKTEncomendaItem ei WHERE ei.numeroNF = :numeroNF AND ei.recordNumber = :recordNumber AND ei.produto = :produto";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("numeroNF", numeroNF);
			qry.setParameter("recordNumber", recordNumber);
			qry.setParameter("produto", produto);
			return (EKTEncomendaItem) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum registro encontrado.");
			return null;
		} 
	}
	
}
