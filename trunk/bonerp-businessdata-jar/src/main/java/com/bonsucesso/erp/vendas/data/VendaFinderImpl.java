package com.bonsucesso.erp.vendas.data;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.bonsucesso.erp.rh.data.FuncionarioFinder;
import com.bonsucesso.erp.rh.model.Funcionario;
import com.bonsucesso.erp.vendas.model.StatusVenda;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de Finder para entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("vendaFinder")
public class VendaFinderImpl extends BasicEntityFinderImpl<Venda> implements VendaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 4424476252876854765L;

	@Override
	public VendaFinder getThiz() {
		return (VendaFinder) super.getThiz();
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Venda findBy(Date dtVenda, Integer pv) throws ViewException {
		final String jpql = "FROM Venda WHERE dtVenda = :dtVenda AND pv = :pv";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtVenda", dtVenda);
		params.put("pv", pv);
		return getThiz().findSingleResult(jpql, params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public Venda findBy(Integer pv, Date mesano) throws ViewException {
		final String jpql = "FROM Venda WHERE mesano = :mesano AND pv = :pv";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mesano", new SimpleDateFormat("yyyyMM").format(mesano));
		params.put("pv", pv);
		return getThiz().findSingleResult(jpql, params, false);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalBy(Date ini, Date fim, Funcionario vendedor) throws ViewException {
		try {
			// ignora as SAÍDAS AUTORIZADAS
			logger.debug("findTotalBy(Date ini = '" + ini + "', Date fim = '" + fim + "', Funcionario vendedor = '"
					+ vendedor + "')");
			String jpql = "SELECT sum(valorTotal) FROM Venda WHERE dtVenda BETWEEN :ini AND :fim AND planoPagto.codigo != '6.00' AND deletado = false";
			if (vendedor != null) {
				jpql += " AND vendedor = :vendedor";
			}
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("ini", CalendarUtil.zeroDate(ini));
			qry.setParameter("fim", CalendarUtil.to235959(fim));

			if (vendedor != null) {
				qry.setParameter("vendedor", vendedor);
			}

			// JPAUtil.cacheOn(qry);
			return (BigDecimal) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalSomenteVendedoresInternosBy(Date mesAno) throws ViewException {

		Date ini = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date fim = CalendarUtil.ultimoDiaNoMesAno(mesAno);
		return getThiz().findTotalSomenteVendedoresInternosBy(ini, fim);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalSomenteVendedoresInternosBy(Date ini, Date fim) throws ViewException {
		try {
			// ignora as SAÍDAS AUTORIZADAS
			String jpql = "SELECT sum(valorTotal) FROM Venda WHERE dtVenda BETWEEN :ini AND :fim AND planoPagto.codigo != '6.00' AND deletado = false AND (vendedor.codigo < 90 OR vendedor.codigo = 99)";

			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("ini", CalendarUtil.zeroDate(ini));
			qry.setParameter("fim", CalendarUtil.to235959(fim));

//			JPAUtil.cacheOff(qry);
			return (BigDecimal) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findVendasSomenteVendedoresInternosBy(Date mesAno) throws ViewException {
		try {
			// ignora as SAÍDAS AUTORIZADAS
			String jpql = "FROM Venda WHERE dtVenda BETWEEN :ini AND :fim AND planoPagto.codigo != '6.00' AND deletado = false AND (vendedor.codigo < 90 OR vendedor.codigo = 99)";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ini", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			params.put("fim", CalendarUtil.ultimoDiaNoMesAno(mesAno));

			return getThiz().findResults(jpql, params);

		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findVendasSomenteExternosBy(Date mesAno) throws ViewException {
		try {
			// ignora as SAÍDAS AUTORIZADAS
			String jpql = "FROM Venda WHERE dtVenda BETWEEN :ini AND :fim AND planoPagto.codigo != '6.00' AND deletado = false AND (vendedor.codigo >= 90 AND vendedor.codigo != 99) ORDER BY pv";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ini", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			params.put("fim", CalendarUtil.ultimoDiaNoMesAno(mesAno));

			return getThiz().findResults(jpql, params);

		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findVendasBy(Date mesAno, Funcionario vendedor) throws ViewException {
		try {
			// ignora as SAÍDAS AUTORIZADAS
			String jpql = "FROM Venda WHERE dtVenda BETWEEN :ini AND :fim AND planoPagto.codigo != '6.00' AND deletado = false AND vendedor = :vendedor ORDER BY pv";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ini", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			params.put("fim", CalendarUtil.ultimoDiaNoMesAno(mesAno));
			params.put("vendedor", vendedor);

			return getThiz().findResults(jpql, params);

		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalBy(Date ini, Date fim) throws ViewException {
		return getThiz().findTotalBy(ini, fim, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalBy(Date mesAno, Funcionario vendedor) throws ViewException {
		Date ini = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date fim = CalendarUtil.ultimoDiaNoMesAno(mesAno);
		return getThiz().findTotalBy(ini, fim, vendedor);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalBy(Date mesAno) throws ViewException {
		logger.debug("findTotalBy(Date mesAno = '" + mesAno + "')");
		Date ini = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date fim = CalendarUtil.ultimoDiaNoMesAno(mesAno);
		return getThiz().findTotalBy(ini, fim, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Integer findQtdeVendasBy(Date ini, Date fim, Funcionario vendedor) throws ViewException {
		try {
			logger.debug("findQtdeVendasBy(Date ini = '" + ini + "', Date fim = '" + fim + "', Funcionario vendedor = '"
					+ vendedor + "')");
			// ignora as SAÍDAS AUTORIZADAS
			String jpql = "SELECT count(*) FROM Venda WHERE dtVenda BETWEEN :ini AND :fim AND planoPagto.codigo != '6.00'";
			if (vendedor != null) {
				jpql += " AND vendedor = :vendedor";
			}
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("ini", CalendarUtil.zeroDate(ini));
			qry.setParameter("fim", CalendarUtil.to235959(fim));

			if (vendedor != null) {
				qry.setParameter("vendedor", vendedor);
			}

			JPAUtil.cacheOn(qry);
			qry.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

			return ((Long) qry.getSingleResult()).intValue();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Integer findQtdeVendasBy(Date ini, Date fim) throws ViewException {
		return getThiz().findQtdeVendasBy(ini, fim, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Integer findQtdeVendasBy(Date mesAno, Funcionario vendedor) throws ViewException {
		Date ini = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date fim = CalendarUtil.ultimoDiaNoMesAno(mesAno);
		return getThiz().findQtdeVendasBy(ini, fim, vendedor);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Integer findQtdeVendasBy(Date mesAno) throws ViewException {
		Date ini = CalendarUtil.primeiroDiaNoMesAno(mesAno);
		Date fim = CalendarUtil.ultimoDiaNoMesAno(mesAno);
		return getThiz().findQtdeVendasBy(ini, fim, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findVendasBy(Date dia) throws ViewException {
		return getThiz().findResults("FROM Venda WHERE dtVenda = :dtVenda", "dtVenda", dia, false);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Map<Funcionario, BigDecimal> findTotalVendasFuncionariosAtivos(Date mesAno) throws ViewException {

		FuncionarioFinder funcionarioFinder = (FuncionarioFinder) getBeanFactory().getBean("funcionarioFinder");

		List<Funcionario> vendedores = funcionarioFinder.findVendedoresAtivosNoMesAno(mesAno);

		Map<Funcionario, BigDecimal> m = new HashMap<Funcionario, BigDecimal>();

		for (Funcionario vendedor : vendedores) {
			m.put(vendedor, getThiz().findTotalBy(mesAno, vendedor));
		}

		return m;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Map<Funcionario, BigDecimal> findTotalVendasFuncionariosTodos(Date mesAno) throws ViewException {

		List<Funcionario> vendedores = getThiz().findFuncionariosComVendaNoMesAno(mesAno);

		Map<Funcionario, BigDecimal> m = new HashMap<Funcionario, BigDecimal>();

		for (Funcionario vendedor : vendedores) {
			logger.debug("Calculando total para " + vendedor.getCodigo() + " - " + vendedor.getNomeEkt());
			BigDecimal bdTotal = getThiz().findTotalBy(mesAno, vendedor);
			if (bdTotal == null) {
				bdTotal = BigDecimal.ZERO;
			}
			logger.debug(" = " + bdTotal.doubleValue());
			m.put(vendedor, bdTotal);
		}

		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Funcionario> findFuncionariosComVendaNoMesAno(Date mesAno) throws ViewException {
		try {

			String jpql = "SELECT distinct(v.vendedor) FROM Venda v WHERE v.dtVenda BETWEEN :ini AND :fim";
			final Query qry = getEntityManager().createQuery(jpql);

			qry.setParameter("ini", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			qry.setParameter("fim", CalendarUtil.ultimoDiaNoMesAno(mesAno));

			JPAUtil.cacheOn(qry);

			return qry.getResultList();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Integer findPosicaoBy(Date mesAno, Funcionario vendedor) throws ViewException {

		// Ordenando um mapa por values
		// Fone: http://www.mkyong.com/java/how-to-sort-a-map-in-java/

		Map<Funcionario, BigDecimal> m = getThiz().findTotalVendasFuncionariosAtivos(mesAno);

		Map<BigDecimal, Funcionario> mInv = new HashMap<BigDecimal, Funcionario>();

		for (Map.Entry<Funcionario, BigDecimal> e : m.entrySet()) {
			mInv.put(e.getValue(), e.getKey());
		}

		Map<BigDecimal, Funcionario> treeMap = new TreeMap<BigDecimal, Funcionario>(new Comparator<BigDecimal>() {

			@Override
			public int compare(BigDecimal o1, BigDecimal o2) {
				return new CompareToBuilder()
						.append(o2, o1)
						.toComparison();
			}
		});

		treeMap.putAll(mInv);

		int i = 0;
		for (Map.Entry<BigDecimal, Funcionario> e : treeMap.entrySet()) {
			i++;
			if (e.getValue().equals(vendedor)) {
				return i;
			}
		}

		return null;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Integer findQtdeDiasComVendasBy(Date mesAno, Funcionario vendedor) throws ViewException {
		try {
			// ignora as SAÍDAS AUTORIZADAS
			String jpql = "SELECT count(*) FROM Venda WHERE dtVenda BETWEEN :ini AND :fim AND planoPagto.codigo != '6.00' AND vendedor = :vendedor GROUP BY dtVenda";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("ini", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			qry.setParameter("fim", CalendarUtil.ultimoDiaNoMesAno(mesAno));
			qry.setParameter("vendedor", vendedor);

			JPAUtil.cacheOn(qry);

			return qry.getResultList().size();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	/**
	 * Verifica se o vendedor deve ser considerado no mês de venda, de acordo com o número de dias trabalhados.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public boolean findConsideraMes(Date mesAno, Funcionario vendedor) throws ViewException {
		//		DiaUtilFinder diaUtilFinder = (DiaUtilFinder) getBeanFactory().getBean("diaUtilFinder");
		//		ConfigFinder configFinder = (ConfigFinder) getBeanFactory().getBean("configFinder");
		//
		//		// obtém o máximo de dias sem vendas para poder considerar o mês
		//		Integer qtdeFaltasMaxConsideraMes = Integer.parseInt(configFinder
		//				.findConfigByChave("bonsucesso.rh.qtdeFaltasMaxConsideraMes").getValor().trim());
		//		Integer qtdeDiasComVendas = getThiz().findQtdeDiasComVendasBy(mesAno, vendedor);
		//		Integer qtdeDiasUteisComerciais = Integer.valueOf(diaUtilFinder.findDiasUteisComerciaisBy(mesAno).size());
		//		if (qtdeDiasUteisComerciais < 10) {
		//			throw new ViewException("Mês com número inválido de dias úteis comerciais: "
		//					+ (new java.text.SimpleDateFormat("MM/yyyy").format(mesAno)));
		//		}
		//
		//		if ((qtdeFaltasMaxConsideraMes == null) || (qtdeDiasComVendas == null) || (qtdeDiasUteisComerciais == null)) {
		//			throw new ViewException("Erro ao calcular 'consideraMes'");
		//		}
		//
		//		return qtdeDiasComVendas >= (qtdeDiasUteisComerciais - qtdeFaltasMaxConsideraMes);

		Integer qtdeTotalVendas = getThiz().findQtdeVendasBy(mesAno);
		Integer qtdeVendasVendedor = getThiz().findQtdeVendasBy(mesAno, vendedor);

		if (qtdeVendasVendedor == 0 || qtdeTotalVendas == 0) {
			return false;
		}

		Double porcentagem = new BigDecimal(qtdeVendasVendedor)
				.divide(new BigDecimal(qtdeTotalVendas), 2, RoundingMode.HALF_DOWN).doubleValue();
		if (porcentagem >= 0.05) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Retorna a qtde de vendedores que estão sendo considerados no mesAno.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	@Cacheable(value = "methodsCache")
	public Integer findQtdeVendedoresConsideraMes(Date mesAno) throws ViewException {
		FuncionarioFinder funcionarioFinder = (FuncionarioFinder) getBeanFactory().getBean("funcionarioFinder");

		int qtdeVendedoresConsideraMes = 0;

		for (Funcionario vendedor : funcionarioFinder.findVendedoresAtivosNoMesAno(mesAno)) {
			if (getThiz().findConsideraMes(mesAno, vendedor)) {
				qtdeVendedoresConsideraMes++;
			}
		}

		return qtdeVendedoresConsideraMes;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Date findUltimoDiaComVendas() throws ViewException {
		try {
			// ignora as SAÍDAS AUTORIZADAS
			String jpql = "SELECT dtVenda FROM Venda ORDER BY dtVenda DESC";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setMaxResults(1);
			return (Date) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao buscar", e);
		}
	}

	/**
	 * Encontra o total de vendas do EKT, que foram importadas, mas somente as que são consideradas "A VISTA".
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalAVistaEKT(Date ini, Date fim, Boolean bonsucesso) throws ViewException {

		String sql = "SELECT sum(v.valor_total) as total " +
				"FROM ven_venda v, ven_plano_pagto pp, rh_funcionario vendedor " +
				"WHERE " +
				"vendedor.id = v.vendedor_id AND " +
				"v.plano_pagto_id = pp.id " +
				"AND v.pv IS NOT NULL " +
				"AND (" +
				"pp.codigo LIKE '1.00' OR " +
				"pp.codigo LIKE '9.99' OR " +
				"pp.codigo LIKE '3.0' OR " +
				"pp.codigo LIKE '5%' OR " +
				"pp.codigo LIKE '2%') AND "
				+ "v.dt_venda BETWEEN :ini AND :fim "
				+ "AND v.deletado = false";

		if (bonsucesso != null) {
			if (bonsucesso == true) {
				sql += " AND (vendedor.codigo < 90 OR vendedor.codigo = 99)";
			} else {
				sql += " AND vendedor.codigo = 95";
			}
		}

		try {
			final Query qry = getEntityManager().createNativeQuery(sql);
			qry.setParameter("ini", ini);
			qry.setParameter("fim", fim);
			return (BigDecimal) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único", e);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new ViewException("Erro ao buscar elemento único", e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findAllBy(String mesano) throws ViewException {
		final String jpql = "FROM Venda WHERE mesano = :mesano";
		return getThiz().findResults(jpql, "mesano", mesano);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Object[]> findQtdeItensVendidosBy(Date mesAnoIni,
			Date mesAnoFim) throws ViewException {

		String jpqlVendas = "SELECT pvr.produto.fornecedor, pvr.produto.subdepto, pvr.mesano, SUM(pvr.qtde) FROM ProdutoVendaResults pvr WHERE "
				+ "pvr.mesano BETWEEN :cMesAnoIni AND :cMesAnoFim "
				+ "GROUP BY pvr.produto.fornecedor.id, pvr.produto.subdepto.id";

		Query qry = getEntityManager().createQuery(jpqlVendas);

		qry.setParameter("cMesAnoIni", CalendarUtil.primeiroDiaNoMesAno(mesAnoIni));
		qry.setParameter("cMesAnoFim", CalendarUtil.ultimoDiaNoMesAno(mesAnoFim));

		return qry.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Map<Subdepartamento, Map<Date, BigDecimal>> findQtdeItensVendidosBy(Fornecedor fornecedor, Date mesAnoIni,
			Date mesAnoFim) throws ViewException {

		Map<Subdepartamento, Map<Date, BigDecimal>> r = new HashMap<Subdepartamento, Map<Date, BigDecimal>>();

		List<Date> mesAnoList = CalendarUtil.buildMesAnoList(mesAnoIni, mesAnoFim);

		String jpqlVendas = "SELECT pvr.produto.subdepto, pvr.mesano, SUM(r.qtde) FROM ProdutoVendaResults pvr WHERE "
				+ "pvr.produto.fornecedor = :fornecedor AND "
				+ "pvr.mesano BETWEEN :cMesAnoIni AND :cMesAnoFim GROUP BY pvr.produto.subdepto, pvr.mesano";

		Query qry = getEntityManager().createQuery(jpqlVendas);
		qry.setParameter("fornecedor", fornecedor);

		qry.setParameter("cMesAnoIni", CalendarUtil.primeiroDiaNoMesAno(mesAnoIni));
		qry.setParameter("cMesAnoFim", CalendarUtil.ultimoDiaNoMesAno(mesAnoFim));

		List<Object[]> l = qry.getResultList();

		for (Object[] i : l) {

			Subdepartamento subdepto = (Subdepartamento) i[0];
			Date mesano = CalendarUtil.primeiroDiaNoMesAno((Date) i[1]);
			BigDecimal qtde = (BigDecimal) i[2];

			Map<Date, BigDecimal> linha;
			if (!r.containsKey(subdepto)) {
				linha = new HashMap<Date, BigDecimal>();
				r.put(subdepto, linha);
				// já preenche com todos os mesano da lista
				for (Date cMesAno : mesAnoList) {
					linha.put(cMesAno, BigDecimal.ZERO);
				}
			} else {
				linha = r.get(subdepto);
			}

			if (!linha.containsKey(mesano)) {
				throw new ViewException("mesano já deveria estar na lista"); // já deveria ter sido preenchido no for acima
			}
			qtde = qtde == null ? BigDecimal.ZERO : qtde;
			linha.put(mesano, qtde);

		}

		return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Map<Date, BigDecimal> findQtdeItensVendidosByFornecedor(Fornecedor fornecedor, Subdepartamento subdepto,
			Date mesAnoIni,
			Date mesAnoFim) throws ViewException {

		Map<Date, BigDecimal> r = new HashMap<Date, BigDecimal>();

		List<Date> mesAnoList = CalendarUtil.buildMesAnoList(mesAnoIni, mesAnoFim);

		String jpqlVendas = "SELECT mesano, SUM(qtde) FROM ProdutoVendaResults WHERE "
				+ "produto.fornecedor = :fornecedor AND "
				+ "produto.subdepto = :subdepto AND "
				+ "mesano BETWEEN :cMesAnoIni AND :cMesAnoFim GROUP BY mesano";

		Query qry = getEntityManager().createQuery(jpqlVendas);
		qry.setParameter("fornecedor", fornecedor);
		qry.setParameter("subdepto", subdepto);

		for (Date cMesAno : mesAnoList) {
			r.put(cMesAno, BigDecimal.ZERO);
		}

		qry.setParameter("cMesAnoIni", CalendarUtil.primeiroDiaNoMesAno(mesAnoIni));
		qry.setParameter("cMesAnoFim", CalendarUtil.ultimoDiaNoMesAno(mesAnoFim));

		List<Object[]> l = qry.getResultList();

		for (Object[] i : l) {
			if (i[0] != null) {
				Date mesano = CalendarUtil.primeiroDiaNoMesAno((Date) i[0]);
				if (!r.containsKey(mesano)) {
					throw new ViewException("mesano já deveria estar na lista");
				}
				BigDecimal qtde = (BigDecimal) i[1];
				qtde = qtde == null ? BigDecimal.ZERO : qtde;
				r.put(mesano, qtde);
			}
		}

		return r;
	}

	/**
	 * Método antigo: procurada direto nas tabelas de vendas. Lento demais.
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Map<Date, BigDecimal> findQtdeItensVendidosBy_direto(Fornecedor fornecedor, Subdepartamento subdepto,
			Date mesAnoIni,
			Date mesAnoFim) throws ViewException {

		Map<Date, BigDecimal> r = new HashMap<Date, BigDecimal>();

		List<Date> mesAnoList = CalendarUtil.buildMesAnoList(mesAnoIni, mesAnoFim);

		String jpqlVendas = "SELECT sum(vi.qtde) FROM VendaItem vi WHERE "
				+ "vi.venda.tipo.id = 1 AND "
				+ "vi.produto.fornecedor = :fornecedor AND "
				+ "vi.produto.subdepto = :subdepto AND "
				+ "vi.produto.reduzidoEkt != 88888 AND "
				+ "vi.venda.dtVenda BETWEEN :cMesAnoIni AND :cMesAnoFim";

		Query qryVendas = getEntityManager().createQuery(jpqlVendas);
		qryVendas.setParameter("fornecedor", fornecedor);
		qryVendas.setParameter("subdepto", subdepto);

		String jpqlTrocas = "SELECT sum(vi.qtde) FROM VendaItem vi WHERE "
				+ "vi.venda.tipo.id = 2 AND "
				+ "vi.produto.fornecedor = :fornecedor AND "
				+ "vi.produto.reduzidoEkt != 88888 AND "
				+ "vi.produto.subdepto = :subdepto AND "
				+ "vi.venda.dtVenda BETWEEN :cMesAnoIni AND :cMesAnoFim";

		Query qryTrocas = getEntityManager().createQuery(jpqlTrocas);
		qryTrocas.setParameter("fornecedor", fornecedor);
		qryTrocas.setParameter("subdepto", subdepto);

		for (Date cMesAno : mesAnoList) {
			qryVendas.setParameter("cMesAnoIni", CalendarUtil.primeiroDiaNoMesAno(cMesAno));
			qryVendas.setParameter("cMesAnoFim", CalendarUtil.ultimoDiaNoMesAno(cMesAno));

			qryTrocas.setParameter("cMesAnoIni", CalendarUtil.primeiroDiaNoMesAno(cMesAno));
			qryTrocas.setParameter("cMesAnoFim", CalendarUtil.ultimoDiaNoMesAno(cMesAno));

			BigDecimal bdVendas = (BigDecimal) qryVendas.getSingleResult();
			bdVendas = bdVendas == null ? BigDecimal.ZERO : bdVendas;

			BigDecimal bdTrocas = (BigDecimal) qryTrocas.getSingleResult();
			bdTrocas = bdTrocas == null ? BigDecimal.ZERO : bdTrocas;

			BigDecimal dif = bdVendas.subtract(bdTrocas);

			r.put(cMesAno, dif);
		}

		return r;
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findQtdeItensVendidosBy(Produto produto, Date mesAno) throws ViewException {

		mesAno = CalendarUtil.primeiroDiaNoMesAno(mesAno);

		String jpqlVendas = "SELECT sum(qtde) FROM ProdutoVendaResults WHERE "
				+ "produto = :produto AND "
				+ "mesAno = :mesAno";

		Query qryVendas = getEntityManager().createQuery(jpqlVendas);
		qryVendas.setParameter("produto", produto);
		qryVendas.setParameter("mesAno", mesAno);
		BigDecimal bdVendas = (BigDecimal) qryVendas.getSingleResult();

		return bdVendas;

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Venda> findVendasComProdutosBy(Fornecedor fornecedor, Subdepartamento subdepto, Date mesAno)
			throws ViewException {
		try {
			final String jpql = "SELECT vi.venda FROM VendaItem vi WHERE vi.produto.fornecedor = :fornecedor AND vi.produto.subdepto = :subdepto AND vi.venda.dtVenda BETWEEN :mesAnoIni AND :mesAnoFim";

			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("fornecedor", fornecedor);
			qry.setParameter("subdepto", subdepto);
			qry.setParameter("mesAnoIni", CalendarUtil.primeiroDiaNoMesAno(mesAno));
			qry.setParameter("mesAnoIni", CalendarUtil.ultimoDiaNoMesAno(mesAno));
			return qry.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar [findVendasComProdutosBy(Fornecedor fornecedor, Subdepartamento subdepto, Date mesAno)]", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED,
			isolation = Isolation.SERIALIZABLE, rollbackFor = ViewException.class)
	public Integer findProximoPV(String mesano) throws ViewException {
		try {

			logger.debug("findProximoPV(String mesano = " + mesano + ")");
			if (mesano == null || "".equals(mesano.trim())) {
				throw new ViewException("mesano inválido");
			}

			try {
				// LOCK NA TABELA inteira. Preciso travar ela inteira pois não adianta travar somente ali embaixo com o 'WHERE mesano = :mesano', porque quando não existir o mesano, o LOCK não vai acontecer.
				String sqlControlePVs = "SELECT coalesce(max(pv),0) + 1 FROM ven_venda WHERE mesano = :mesano ORDER BY pv DESC FOR UPDATE";
				Query qryControlePVs = getEntityManager().createNativeQuery(sqlControlePVs);
				qryControlePVs.setParameter("mesano", mesano);
				logger.debug("EXECUTANDO A QUERY COM LOCK 'FOR UPDATE' NA ven_venda ...");
				BigInteger proxPV = (BigInteger) qryControlePVs.getResultList().get(0); // pego todos para funcionar corretamente o 'FOR UPDATE'
				return proxPV.intValue();
			} catch (Exception e) {
				throw new ViewException("Erro ao tentar lock na ven_venda");
			}

		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar próximo número de PV", e);
		}
	}

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED,
			isolation = Isolation.SERIALIZABLE)
	public Integer findProximoPV_old(String mesano) throws ViewException {
		try {

			logger.debug("findProximoPV(String mesano = " + mesano + ")");
			if (mesano == null || "".equals(mesano.trim())) {
				throw new ViewException("mesano inválido");
			}

			try {
				// LOCK NA TABELA inteira. Preciso travar ela inteira pois não adianta travar somente ali embaixo com o 'WHERE mesano = :mesano', porque quando não existir o mesano, o LOCK não vai acontecer.
				String sqlControlePVs = "SELECT pv FROM ven_venda_pvs WHERE mesano IS NOT NULL FOR UPDATE";
				Query qryControlePVs = getEntityManager().createNativeQuery(sqlControlePVs);
				qryControlePVs.getResultList();
			} catch (Exception e) {
				throw new ViewException("Erro ao tentar lock na ven_venda_pvs");
			}

			try {
				// Primeiro verifica qual o último PV na tabela de controle de numeração de PVs
				String sqlControlePVs = "SELECT pv FROM ven_venda_pvs WHERE mesano = :mesano";
				Query qryControlePVs = getEntityManager().createNativeQuery(sqlControlePVs);
				qryControlePVs.setParameter("mesano", mesano);
				qryControlePVs.getSingleResult();
			} catch (NoResultException e) {
				// Se não achar o resultado, insere um novo registro para este mês ano (lembrando que ali embaixo é que vai ser incrementado).				
				String sqlInsertControlePVs = "INSERT INTO ven_venda_pvs(mesano,pv) VALUES(:mesano, coalesce( (select max(pv) from ven_venda where mesano = :mesano), 0));";
				Query qryInsertControlePVs = getEntityManager().createNativeQuery(sqlInsertControlePVs);
				qryInsertControlePVs.setParameter("mesano", mesano);
				qryInsertControlePVs.executeUpdate();
			}

			// String sqlUpdate = "INSERT INTO ven_venda_pvs(mesano, pv) VALUES(:mesano, (select max(pv)+1 from ven_venda where mesano = :mesano)) ON DUPLICATE KEY UPDATE pv = (select max(pv)+1 from ven_venda where mesano = :mesano);";
			// String sqlUpdate = "INSERT INTO ven_venda_pvs(mesano, pv) VALUES(:mesano, (select max(pv)+1 from ven_venda where mesano = :mesano)) ON DUPLICATE KEY UPDATE pv = pv+1";
			try {
				String sqlUpdateIncrementa = "UPDATE ven_venda_pvs SET pv = pv + 1 WHERE mesano = :mesano";
				Query qryUpdateIncrementa = getEntityManager()
						.createNativeQuery(sqlUpdateIncrementa);
				qryUpdateIncrementa.setParameter("mesano", mesano);
				qryUpdateIncrementa.executeUpdate();
			} catch (Exception e) {
				throw new ViewException("Erro ao incrementar pv.");
			}

			try {
				String sqlControlePVs = "SELECT pv FROM ven_venda_pvs WHERE mesano = :mesano";
				Query qryControlePVs = getEntityManager().createNativeQuery(sqlControlePVs);
				qryControlePVs = getEntityManager().createNativeQuery(sqlControlePVs);
				qryControlePVs.setParameter("mesano", mesano);
				Integer proxPV = (Integer) qryControlePVs.getSingleResult();
				return proxPV;
			} catch (Exception e) {
				throw new ViewException("Erro no lock da tabela ven_venda_pvs.");
			}

		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar próximo número de PV", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findVendasASeremFaturadas(Date dtVenda) throws ViewException {
		try {
			final String jpql = "SELECT v.id FROM "
					+ "ven_venda v "
					+ "LEFT JOIN fis_nf_venda nf_venda ON v.id = nf_venda.venda_id "
					+ "LEFT JOIN fis_nf nf ON (nf_venda.nota_fiscal_id = nf.id AND (nf.spartacus_status IS NULL OR nf.spartacus_status = -1 OR nf.spartacus_status = 0)) "
					+ "WHERE "
					+ "v.dt_venda = :dtVenda AND "
					+ "v.status = 'PREVENDA' "
					+ "ORDER BY v.dt_venda DESC";
			final Query qry = getEntityManager().createNativeQuery(jpql);
			qry.setParameter("dtVenda", CalendarUtil.zeroDate(dtVenda));

			// Tive que fazer assim porque não estava aceitando a query passando direto o Venda.class
			List<BigInteger> l = qry.getResultList();
			if (l != null) {
				List<Venda> r = new ArrayList<Venda>();
				for (BigInteger vendaId : l) {
					r.add(getThiz().findById(vendaId.longValue()));
				}
				return r;
			}
			return null;
		} catch (Exception e) {
			logger.error("Erro ao pesquisar - findVendasASeremFaturadas()");
			throw new ViewException("Erro ao pesquisar - findVendasASeremFaturadas()", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findBy(Date dtVenda, StatusVenda status) throws ViewException {
		try {
			final String jpql = "FROM Venda v "
					+ "WHERE "
					+ "v.dtVenda = :dtVenda AND "
					+ "v.status = :status "
					+ "ORDER BY v.dtVenda DESC";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("dtVenda", CalendarUtil.zeroDate(dtVenda));
			qry.setParameter("status", status);
			return qry.getResultList();
		} catch (Exception e) {
			logger.error("Erro ao pesquisar - findVendasASeremFaturadas()");
			throw new ViewException("Erro ao pesquisar - findVendasASeremFaturadas()", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Venda> findBy(Date dtVenda) throws ViewException {
		try {
			final String jpql = "FROM Venda v "
					+ "WHERE "
					+ "v.dtVenda = :dtVenda "
					+ "ORDER BY v.dtVenda DESC";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("dtVenda", CalendarUtil.zeroDate(dtVenda));
			return qry.getResultList();
		} catch (Exception e) {
			logger.error("Erro ao pesquisar - findVendasASeremFaturadas()");
			throw new ViewException("Erro ao pesquisar - findVendasASeremFaturadas()", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Venda> findVendasAguardandoStatusNotaFiscalReceita() throws ViewException {
		final String jpql = "FROM Venda v WHERE v.notaFiscal.status = -1 OR v.notaFiscal.status = 0 OR v.notaFiscal.status IS NULL";
		return getThiz().findResults(jpql, null, false);
	}

}
