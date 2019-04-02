package com.bonsucesso.erp.ekt.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTVendaItem;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTVendaItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektVendaItemFinder")
public class EKTVendaItemFinderImpl extends BasicEntityFinderImpl<EKTVendaItem> implements EKTVendaItemFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 700478853957577037L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<EKTVendaItem> findAllBy(String mesano) throws ViewException {
		final String jpql = "FROM EKTVendaItem vi WHERE mesano = :mesano";
		final Query qry = getEntityManager().createQuery(jpql);
		qry.setParameter("mesano", mesano);
		return qry.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<EKTVendaItem> findBy(Double pv, String mesano) throws ViewException {
		final String jpql = "FROM EKTVendaItem vi WHERE vi.numeroNF = :pv AND mesano = :mesano";
		final Query qry = getEntityManager().createQuery(jpql);
		qry.setParameter("pv", pv);
		qry.setParameter("mesano", mesano);
		return qry.getResultList();
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public EKTVendaItem findBy(Double numeroNF, Integer recordNumber, Double produto, String mesano) throws ViewException {
		try {
			final String jpql = "FROM EKTVendaItem vi WHERE vi.numeroNF = :numeroNF AND vi.recordNumber = :recordNumber AND vi.produto = :produto AND vi.mesano = :mesano";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("numeroNF", numeroNF);
			qry.setParameter("recordNumber", recordNumber);
			qry.setParameter("produto", produto);
			qry.setParameter("mesano", mesano);
			return (EKTVendaItem) qry.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error(e);
			throw new ViewException(e);
		} catch (NoResultException e) {
			logger.info("Nenhum registro encontrado.");
			return null;
		} 
	}

}
