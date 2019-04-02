package com.bonsucesso.erp.base.data;



import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.base.model.DiaUtil;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de Finder para entidade DiaUtil.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("diaUtilFinder")
public class DiaUtilFinderImpl extends BasicEntityFinderImpl<DiaUtil> implements DiaUtilFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 8979036806525347837L;

	protected static Logger logger = Logger.getLogger(DiaUtilFinderImpl.class);

	@Override
	public DiaUtilFinder getThiz() {
		return (DiaUtilFinder) super.getThiz();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DiaUtil> findDiasUteisBy(Date ini, Date fim, Boolean comercial, Boolean financeiro)
			throws ViewException {
		try {
			String jpql = "FROM DiaUtil WHERE dia BETWEEN :ini AND :fim ";
			if (comercial != null) {
				jpql += " AND comercial = :comercial";
			}
			if (financeiro != null) {
				jpql += " AND financeiro = :financeiro";
			}
			jpql += " ORDER BY dia";

			final Query qry = getEntityManager().createQuery(jpql, DiaUtil.class);

			JPAUtil.cacheOn(qry);
			
			qry.setParameter("ini", CalendarUtil.zeroDate(ini));
			qry.setParameter("fim", CalendarUtil.to235959(fim));
			if ((comercial != null) && comercial.equals(Boolean.TRUE)) {
				qry.setParameter("comercial", comercial);
			}
			if ((financeiro != null) && financeiro.equals(Boolean.TRUE)) {
				qry.setParameter("financeiro", financeiro);
			}
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar dias úteis no período", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DiaUtil> findDiasUteisBy(Date ini, Date fim)
			throws ViewException {
		return getThiz().findDiasUteisBy(ini, fim, null, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DiaUtil> findDiasUteisBy(Date mesAno) throws ViewException {
		return getThiz()
				.findDiasUteisBy(CalendarUtil.primeiroDiaNoMesAno(mesAno), CalendarUtil.ultimoDiaNoMesAno(mesAno));
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DiaUtil> findDiasUteisFinanceiroBy(Date ini, Date fim) throws ViewException {
		return getThiz().findDiasUteisBy(ini, fim, null, true);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DiaUtil> findDiasUteisFinanceiroBy(Date mesAno) throws ViewException {
		return getThiz()
				.findDiasUteisFinanceiroBy(CalendarUtil.primeiroDiaNoMesAno(mesAno), CalendarUtil
						.ultimoDiaNoMesAno(mesAno));
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DiaUtil> findDiasUteisComerciaisBy(Date ini, Date fim) throws ViewException {
		return getThiz().findDiasUteisBy(ini, fim, true, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<DiaUtil> findDiasUteisComerciaisBy(Date mesAno) throws ViewException {
		return getThiz()
				.findDiasUteisComerciaisBy(CalendarUtil.primeiroDiaNoMesAno(mesAno), CalendarUtil
						.ultimoDiaNoMesAno(mesAno));
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public DiaUtil findBy(Date dia) throws ViewException {
		final String jpql = "FROM DiaUtil WHERE dia = :dia";
		return getThiz().findSingleResult(jpql, "dia", CalendarUtil.zeroDate(dia));
	}

	/**
	 * Retorna o próximo dia útil financeiro (incluindo o dia passado).
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Date findProximoDiaUtilFinanceiro(Date dia) throws ViewException {
		List<DiaUtil> lista = getThiz().findDiasUteisFinanceiroBy(dia, CalendarUtil.incDias(dia, 20));
		if ((lista == null) || (lista.size() < 1)) {
			throw new ViewException("Nenhum dia útil encontrado.");
		}
		return lista.get(0).getDia();
	}

	/**
	 * Retorna o dia útil financeiro anterior ao dia passado.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Date findAnteriorDiaUtilFinanceiro(Date dia) throws ViewException {
		List<DiaUtil> lista = getThiz().findDiasUteisFinanceiroBy(CalendarUtil.incDias(dia, -20), dia);
		if ((lista == null) || (lista.size() < 1)) {
			throw new ViewException("Nenhum dia útil encontrado.");
		}
		return lista.get(lista.size() - 2).getDia();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public DiaUtil findBy(Date mesAno, int ordinal) throws ViewException {
		List<DiaUtil> diasUteisNoMesAno = getThiz().findDiasUteisFinanceiroBy(mesAno);
		return diasUteisNoMesAno.get(ordinal - 1);
	}

	/**
	 * Retorna o próximo dia útil comercial (incluindo o dia passado).
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Date findProximoDiaUtilComercial(Date dia) throws ViewException {
		List<DiaUtil> lista = getThiz().findDiasUteisComerciaisBy(dia, CalendarUtil.incDias(dia, 20));
		if ((lista == null) || (lista.size() < 1)) {
			throw new ViewException("Nenhum dia útil encontrado.");
		}
		return lista.get(0).getDia();
	}

	/**
	 * Retorna o dia útil comercial anterior ao dia passado.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Date findAnteriorDiaUtilComercial(Date dia) throws ViewException {
		List<DiaUtil> lista = getThiz().findDiasUteisComerciaisBy(CalendarUtil.incDias(dia, -20), dia);
		if ((lista == null) || (lista.size() < 1)) {
			throw new ViewException("Nenhum dia útil encontrado.");
		}
		return lista.get(lista.size() - 2).getDia();
	}
}
