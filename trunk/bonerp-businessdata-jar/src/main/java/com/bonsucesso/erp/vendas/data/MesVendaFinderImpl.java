package com.bonsucesso.erp.vendas.data;



import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.model.MesVenda;
import com.bonsucesso.erp.vendas.model.MesVendaItem;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.config.data.ConfigFinder;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de Finder para entidade MesVendaq.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("mesVendaFinder")
public class MesVendaFinderImpl extends BasicEntityFinderImpl<MesVenda> implements MesVendaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -894871714060018659L;

	@Autowired
	private ConfigFinder configFinder;

	@Autowired
	private VendaFinder vendaFinder;

	@Override
	public MesVendaFinder getThiz() {
		return (MesVendaFinder) super.getThiz();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Integer findQtdeVendedoresConsideraMes(Date mesAno) throws ViewException {
		if (mesAno == null) {
			return null;
		}
		Date ini = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date fim = CalendarUtil.ultimoDiaNoMesAno(mesAno);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ini", ini);
		params.put("fim", fim);

		Query qry = getEntityManager()
				.createQuery("SELECT count(*) FROM MesVendaItem WHERE mesVenda.mesAno BETWEEN :ini AND :fim AND consideraMes = true");
		qry.setParameter("ini", ini);
		qry.setParameter("fim", fim);

		Object r = qry.getSingleResult();
		if (r != null && r instanceof Long) {
			return ((Long) r).intValue();
		} else {
			return null;
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public MesVenda findByMesAno(Date mesAno) throws ViewException {
		if (mesAno == null) {
			return null;
		}
		Date ini = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date fim = CalendarUtil.ultimoDiaNoMesAno(mesAno);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ini", ini);
		params.put("fim", fim);

		return getThiz().findSingleResult("FROM MesVenda WHERE mesAno BETWEEN :ini AND :fim", params);
	}

	/**
	 * Retorna a meta mínima para o determinado mês (meta do ano anterior + inflação).
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	@Cacheable(value = "methodsCache")
	public BigDecimal findMetaMinima(Date mesAno) throws ViewException {
		Calendar umAnoAtras = CalendarUtil.getCalendar(mesAno);
		umAnoAtras.add(Calendar.YEAR, -1);

		MesVenda mesVenda = getThiz().findByMesAno(mesAno);

		if (mesVenda == null) {
			throw new ViewException("mesVenda null");
		}

		BigDecimal bdTotalUmAnoAtras = getVendaFinder().findTotalBy(umAnoAtras.getTime());

		// 1 + (inflacao / 100)
		return bdTotalUmAnoAtras.multiply(BigDecimal.ONE.add(mesVenda.getInflacao()
				.divide(new BigDecimal("100.00"), 20, RoundingMode.HALF_DOWN)));
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public MesVenda findUltimoMesCadastrado() throws ViewException {
		final String jpql = "FROM MesVenda ORDER BY mesAno DESC";
		return getThiz().findResults(jpql, null).get(0);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Integer findMediaUltimosMeses(Funcionario vendedor, Date desde, int qtdeMeses) throws ViewException {
		final String sql = "SELECT posicao FROM ven_mes_venda_item i, ven_mes_venda v WHERE v.id = i.mes_venda_id AND vendedor_id = :vendedor_id AND i.considera_mes = true AND mes_ano <= :desde ORDER BY v.mes_ano DESC LIMIT :qtdeMeses";
		Query qry = getEntityManager().createNativeQuery(sql);
		qry.setParameter("vendedor_id", vendedor.getId());
		qry.setParameter("desde", desde);
		qry.setParameter("qtdeMeses", qtdeMeses);

		double soma = 0.0;

		if (qry.getResultList() == null || qry.getResultList().size() < 1) {
			return 0;
		}

		for (Object o : qry.getResultList()) {
			if (o == null)
				continue;
			soma += (Integer) o;
			System.out.println(o);
		}

		BigDecimal bdMedia = new BigDecimal(soma)
				.divide(new BigDecimal(qry.getResultList().size()), 0, RoundingMode.DOWN);
		return bdMedia.intValue();
	}

	public ConfigFinder getConfigFinder() {
		return configFinder;
	}

	public void setConfigFinder(ConfigFinder configFinder) {
		this.configFinder = configFinder;
	}

	public VendaFinder getVendaFinder() {
		return vendaFinder;
	}

	public void setVendaFinder(VendaFinder vendaFinder) {
		this.vendaFinder = vendaFinder;
	}

	@Override
	public MesVendaItem findMesVendaItemBy(Funcionario vendedor, Date mesAno) throws ViewException {
		try {
			String jpql = "FROM MesVendaItem mvi WHERE mvi.vendedor = :vendedor AND mvi.mesVenda.mesAno = :mesAno";
			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("vendedor", vendedor);
			qry.setParameter("mesAno", CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.zeroDate(mesAno)) );
			return (MesVendaItem) qry.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			throw new ViewException(e);
		}
				
	}

}
