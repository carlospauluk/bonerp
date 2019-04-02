package com.bonsucesso.erp.ekt.data;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de Finder para entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektProdutoFinder")
public class EKTProdutoFinderImpl extends BasicEntityFinderImpl<EKTProduto> implements EKTProdutoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -8053300555859680942L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public EKTProduto findByReduzido(Integer reduzido, String mesano) throws ViewException {
		final String jpql = "FROM EKTProduto WHERE reduzido = :reduzido AND mesano = :mesano";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("reduzido", reduzido.doubleValue());
		params.put("mesano", mesano);
		return getThiz().findSingleResult(jpql, params);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true, isolation = Isolation.READ_COMMITTED)
	public List<EKTProduto> findAllLimit(int inicio, int fim, String mesano) throws ViewException {
		final String sql = "SELECT * FROM ekt_produto WHERE mesano = :mesano ORDER BY REDUZIDO LIMIT :inicio,:fim";
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, EKTProduto.class);
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
	@SuppressWarnings("unchecked")
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<EKTProduto> findAtualizadosLimit(Date dtUltAlt, int inicio, int fim, String mesano)
			throws ViewException {
		final String sql = "SELECT * FROM ekt_produto WHERE mesano = :mesano AND DT_ULTALT >= :dtUltAlt ORDER BY REDUZIDO LIMIT :inicio,:fim";
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, EKTProduto.class);
			qry.setParameter("inicio", inicio);
			qry.setParameter("fim", fim);
			qry.setParameter("mesano", mesano);
			qry.setParameter("dtUltAlt", dtUltAlt);
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

	/**
	 * Encontra todos os ekt_produto de um determinado reduzido que estejam entre mesAnoIni e mesAnoFim.
	 * 
	 * @param reduzido
	 * @param mesAnoIni
	 * @param mesAnoFim
	 * @return
	 * @throws ViewException
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<EKTProduto> findBetweenMesesAnos(Integer reduzido, Date mesAnoIni, Date mesAnoFim)
			throws ViewException {
		List<Date> mesesAnos = CalendarUtil.buildMesAnoList(mesAnoIni, mesAnoFim);
		List<String> mesesAnosStr = new ArrayList<String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		for (Date d : mesesAnos) {
			mesesAnosStr.add(sdf.format(d));
		}

		final String sql = "FROM EKTProduto WHERE reduzido = :reduzido AND mesano IN (:mesesAnos)";
		try {
			final Query qry = getEntityManager().createQuery(sql);
			qry.setParameter("reduzido", reduzido.doubleValue());
			qry.setParameter("mesesAnos", mesesAnosStr);
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}

	}

}
