package com.bonsucesso.erp.ekt.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTVenda;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektVendaFinder")
public class EKTVendaFinderImpl extends BasicEntityFinderImpl<EKTVenda> implements EKTVendaFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6149501745812221283L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public List<EKTVenda> findAllValidas(int inicio, int fim, String mesano) throws ViewException {
		final String sql = "SELECT * FROM ekt_venda WHERE mesano = :mesano ORDER BY numero LIMIT :inicio,:fim";
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, EKTVenda.class);
			qry.setParameter("inicio", inicio);
			qry.setParameter("fim", fim);
			qry.setParameter("mesano", mesano);
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
	public EKTVenda findBy(Double numero, String mesano) throws ViewException {
		final String sql = "SELECT * FROM ekt_venda WHERE numero = :numero AND mesano = :mesano";
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, EKTVenda.class);
			qry.setParameter("numero", numero);
			qry.setParameter("mesano", mesano);
			return (EKTVenda) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

}
