package com.bonsucesso.erp.financeiro.data;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.Grupo;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de Finder para entidade GrupoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("grupoItemFinder")
public class GrupoItemFinderImpl extends BasicEntityFinderImpl<GrupoItem> implements
		GrupoItemFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 1669171918147964127L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GrupoItem findBy(Grupo grupo, Date mesAno) throws ViewException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("grupo", grupo);
		params.put("dtVenctoIni", CalendarUtil.primeiroDiaNoMesAno(mesAno));
		params.put("dtVenctoFim", CalendarUtil.ultimoDiaNoMesAno(mesAno));
		final String jpql = "FROM GrupoItem gi WHERE gi.pai = :grupo AND gi.dtVencto BETWEEN :dtVenctoIni AND :dtVenctoFim";
		return getThiz().findSingleResult(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<GrupoItem> findBy(Date mesAno) throws ViewException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtVenctoIni", CalendarUtil.primeiroDiaNoMesAno(mesAno));
		params.put("dtVenctoFim", CalendarUtil.ultimoDiaNoMesAno(mesAno));
		final String jpql = "FROM GrupoItem gi WHERE gi.dtVencto BETWEEN :dtVenctoIni AND :dtVenctoFim";
		return getThiz().findResults(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GrupoItem findUltimo(Grupo grupo) throws ViewException {
		final String jpql = "FROM GrupoItem gi WHERE gi.pai = :grupo ORDER BY gi.dtVencto DESC";
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("grupo", grupo);
			qry.setMaxResults(1);
			return (GrupoItem) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Mais de um resultado encontrado", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GrupoItem findUltimoAnterior(Grupo grupo, Date mesAno) throws ViewException {
		final String jpql = "FROM GrupoItem gi WHERE gi.pai = :grupo AND gi.dtVencto < :dtVencto ORDER BY gi.dtVencto DESC";
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("grupo", grupo);
			qry.setParameter("dtVencto", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			qry.setMaxResults(1);
			return (GrupoItem) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Mais de um resultado encontrado", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GrupoItem findPrimeiroPosterior(Grupo grupo, Date mesAno) throws ViewException {
		final String jpql = "FROM GrupoItem gi WHERE gi.pai = :grupo AND gi.dtVencto > :dtVencto ORDER BY gi.dtVencto ASC";
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("grupo", grupo);
			qry.setParameter("dtVencto", CalendarUtil.ultimoDiaNoMesAno(mesAno));
			qry.setMaxResults(1);
			return (GrupoItem) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Mais de um resultado encontrado", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<GrupoItem> findAllAbertosBy(Grupo grupo) throws ViewException {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("grupo", grupo);
		final String jpql = "FROM GrupoItem gi WHERE gi.fechado = false AND gi.pai = :grupo ORDER BY dtVencto DESC";
		return getThiz().findResults(jpql, params);
	}

}
