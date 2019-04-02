package com.bonsucesso.erp.ekt.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTEncomenda;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTEncomenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektEncomendaFinder")
public class EKTEncomendaFinderImpl extends BasicEntityFinderImpl<EKTEncomenda> implements EKTEncomendaFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6149501745812221283L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public List<EKTEncomenda> findAllValidas(int inicio, int fim) throws ViewException {
		final String sql = "SELECT * FROM ekt_encomenda ORDER BY numero LIMIT :inicio,:fim";
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, EKTEncomenda.class);
			qry.setParameter("inicio", inicio);
			qry.setParameter("fim", fim);
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public EKTEncomenda findBy(Double numero) throws ViewException {
		final String sql = "SELECT * FROM ekt_encomenda WHERE numero = :numero";
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, EKTEncomenda.class);
			qry.setParameter("numero", numero);
			return (EKTEncomenda) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

}
