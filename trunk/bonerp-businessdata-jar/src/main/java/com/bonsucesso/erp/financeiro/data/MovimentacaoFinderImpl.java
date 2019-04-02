package com.bonsucesso.erp.financeiro.data;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.bonsucesso.erp.financeiro.model.Cadeia;
import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.Categoria;
import com.bonsucesso.erp.financeiro.model.GrupoItem;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.bonsucesso.erp.financeiro.model.Status;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.calendar.CalendarUtil;


/**
 * Implementação de Finder para entidade Movimentacao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("movimentacaoFinder")
public class MovimentacaoFinderImpl extends BasicEntityFinderImpl<Movimentacao>
		implements MovimentacaoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 689435377567107293L;

	public enum TipoSaldo {
		SALDO_ANTERIOR_REALIZADAS,
		SALDO_ANTERIOR_COM_CHEQUES,
		SALDO_POSTERIOR_REALIZADAS,
		SALDO_POSTERIOR_COM_CHEQUES;

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findSaldo(Date dtPagto, Carteira carteira,
			@NotNull(message = "tipoSaldo deve ser informado") TipoSaldo tipoSaldo)
			throws ViewException {
		List<Carteira> carteiras = new ArrayList<Carteira>();
		carteiras.add(carteira);
		return getThiz().findSaldo(dtPagto, carteiras, tipoSaldo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findSaldo(Date dtPagto, List<Carteira> carteiras,
			@NotNull(message = "tipoSaldo deve ser informado") TipoSaldo tipoSaldo)
			throws ViewException {

		dtPagto = CalendarUtil.zeroDate(dtPagto);

		// Se for saldo anterior, então decrementa 1 dia para poder usar o <=
		if (tipoSaldo.equals(TipoSaldo.SALDO_ANTERIOR_REALIZADAS)
				|| tipoSaldo.equals(TipoSaldo.SALDO_ANTERIOR_COM_CHEQUES)) {
			dtPagto = CalendarUtil.incDias(dtPagto, -1);
		}

		String jpql = "SELECT SUM( IF (codigo_super=1,m.valor_total,m.valor_total*-1) ) "
				+
				"FROM " +
				"fin_movimentacao m, fin_categoria categ, fin_modo modo " +
				"WHERE " +
				"m.categoria_id = categ.id AND " +
				"m.modo_id = modo.id AND " +
				"m.carteira_id IN :carteiras " +
				"AND ( (m.status = 'REALIZADA' AND m.dt_pagto <= :dtPagto ) ";

		if (tipoSaldo.equals(TipoSaldo.SALDO_POSTERIOR_COM_CHEQUES)
				|| tipoSaldo.equals(TipoSaldo.SALDO_ANTERIOR_COM_CHEQUES)) {
			jpql += " OR (m.status = 'REALIZADA' AND modo.descricao LIKE 'CHEQUE%' AND m.dt_vencto_efetiva <= :dtPagto AND m.dt_pagto > :dtPagto)"
					+
					" OR (m.status = 'A_COMPENSAR' AND m.dt_vencto_efetiva <= :dtPagto)";
		}
		jpql += ")";

		try {
			final Query qry = getEntityManager().createNativeQuery(jpql);
			qry.setParameter("dtPagto", dtPagto);

			List<Long> ids = new ArrayList<Long>();
			for (Carteira c : carteiras) {
				ids.add(c.getId());
			}

			qry.setParameter("carteiras", ids);
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
			throw new ViewException(e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findSaldo(GrupoItem grupoItem) throws ViewException {

		String jpql = "SELECT SUM( IF (codigo_super=1,m.valor_total,m.valor_total*-1) ) "
				+
				"FROM " +
				"fin_movimentacao m, fin_categoria categ " +
				"WHERE " +
				"m.categoria_id = categ.id AND " +
				"m.grupo_item_id = :grupoItemId";

		try {
			final Query qry = getEntityManager().createNativeQuery(jpql);
			qry.setParameter("grupoItemId", grupoItem.getId());

			BigDecimal r = (BigDecimal) qry.getSingleResult();
			return r != null ? r.abs() : BigDecimal.ZERO;
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
	public BigDecimal findTotal(Date dtIni, Date dtFim, Carteira carteira, Integer entradaOuSaida)
			throws ViewException {
		final StringBuilder jpqlEntradas = new StringBuilder();

		if (!entradaOuSaida.equals(1) && !entradaOuSaida.equals(2)) {
			throw new IllegalStateException("entradaOuSaida deve ser '1' ou '2'");
		}

		if ((dtIni == null) || (dtFim == null)) {
			throw new IllegalStateException("Dt Ini e Dt Fim devem ser informadas");
		}

		jpqlEntradas.append("SELECT SUM(e.valor_total) " +
				"FROM fin_movimentacao e INNER JOIN fin_categoria ec ON e.categoria_id = ec.id " +
				"WHERE ec.codigo_super=:entradaOuSaida " +
				"AND " +
				"(" +
				"((e.dt_pagto BETWEEN :dtIni AND :dtFim) AND (e.status = 'REALIZADA')) " +
				"OR " +
				"((e.dt_vencto_efetiva <= :dtFim) AND (e.status IN ('A_COMPENSAR')))" +
				")");

		dtIni = dtIni != null ? dtIni : CalendarUtil.getDate(01, 01, 01);
		dtFim = dtFim != null ? dtFim : CalendarUtil.getDate(01, 01, 9999);

		if (carteira != null) {
			jpqlEntradas.append(" AND e.carteira_id = :carteira_id");
		}

		try {
			final Query qryEntradas = getEntityManager().createNativeQuery(jpqlEntradas.toString());
			qryEntradas.setParameter("entradaOuSaida", entradaOuSaida);
			qryEntradas.setParameter("dtIni", CalendarUtil.zeroDate(dtIni));
			qryEntradas.setParameter("dtFim", CalendarUtil.to235959(dtFim));
			if (carteira != null) {
				qryEntradas.setParameter("carteira_id", carteira.getId());
			}
			Object r = qryEntradas.getSingleResult();
			if (r == null) {
				return BigDecimal.ZERO;
			} else {
				if (entradaOuSaida == 2) {
					return ((BigDecimal) r).abs().negate();
				} else {
					return ((BigDecimal) r);
				}
			}

		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único", e);
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar", e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotalMovimentacoesSaldoComCheques(Date dtPagto, Carteira carteira,
			@NotNull(message = "tipoSaldo deve ser informado") TipoSaldo tipoSaldo) throws ViewException {

		dtPagto = CalendarUtil.zeroDate(dtPagto);

		// Se for saldo anterior, então decrementa 1 dia para poder usar o <=
		if (tipoSaldo.equals(TipoSaldo.SALDO_ANTERIOR_REALIZADAS)
				|| tipoSaldo.equals(TipoSaldo.SALDO_ANTERIOR_COM_CHEQUES)) {
			dtPagto = CalendarUtil.incDias(dtPagto, -1);
		}

		String jpql = "SELECT SUM( IF (categ.codigo_super=1,m.valor_total,m.valor_total*-1) ) "
				+
				"FROM "
				+
				"fin_movimentacao m, fin_categoria categ, fin_modo modo "
				+
				"WHERE "
				+
				"m.categoria_id = categ.id AND "
				+
				"m.modo_id = modo.id AND "
				+
				"m.carteira_id = :carteira AND ("
				+
				"(m.status = 'REALIZADA' AND modo.descricao LIKE 'CHEQUE%' AND m.dt_vencto_efetiva <= :dtPagto AND m.dt_pagto > :dtPagto) "
				+
				"OR (m.status = 'A_COMPENSAR' AND m.dt_vencto_efetiva <= :dtPagto))";

		try {
			final Query qry = getEntityManager().createNativeQuery(jpql);
			qry.setParameter("dtPagto", dtPagto);
			qry.setParameter("carteira", carteira.getId());
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
	public List<Movimentacao> findBy(Date dtMoviment, BigDecimal valorTotal, GrupoItem grupoItem)
			throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtMoviment", dtMoviment);
		params.put("valorTotal", valorTotal);
		params.put("grupoItem", grupoItem);

		return getThiz().findResults("FROM Movimentacao " +
				"WHERE " +
				"dtMoviment = :dtMoviment AND " +
				"valorTotal = :valorTotal AND " +
				"grupoItem = :grupoItem", params, false);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Movimentacao> findBy(Categoria categoria, Date dtPagtoIni, Date dtPagtoFim)
			throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtPagtoIni", dtPagtoIni);
		params.put("dtPagtoFim", dtPagtoFim);
		params.put("categoria", categoria);

		return getThiz().findResults("FROM Movimentacao " +
				"WHERE " +
				"dtPagto BETWEEN :dtPagtoIni AND :dtPagtoFim AND " +
				"categoria = :categoria", params);
	}

	/**
	 *
	 * @param dtVenctoEfetiva
	 * @param valorTotal
	 * @param carteira
	 * @param entrada
	 * @return
	 * @throws ViewException
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Movimentacao> findBy(Date dtUtil, BigDecimal valorTotal, Long entradaOuSaida,
			Modo modo, List<Status> statuss, List<Carteira> carteiras, String numCheque)
			throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtUtil", dtUtil);
		params.put("valorTotal", valorTotal);

		if (carteiras != null) {
			params.put("carteiras", carteiras);
		}
		
		if (numCheque != null) {
			params.put("numCheque", "%" + numCheque);
		}

		if (statuss != null) {

			//			StringBuilder sbStatus = new StringBuilder();
			//			sbStatus.append("'").append(statuss.get(0)).append("'");
			//
			//			for (int i=1 ; i<statuss.size() ; i++) {
			//				sbStatus.append(",'").append(statuss.get(i)).append("'");
			//			}
			//
			//			params.put("statuss", sbStatus.toString());
			params.put("status", statuss);
		}

		params.put("entradaOuSaida", entradaOuSaida);

		String pesqPorModo = "";
		if (modo != null) {
			params.put("modo", modo);
			pesqPorModo = "modo = :modo AND ";
		}

		return getThiz().findResults("FROM Movimentacao " +
				"WHERE " +
				"dtUtil = :dtUtil AND " +
				"valorTotal = :valorTotal AND " +
				(carteiras != null ? "carteira IN :carteiras AND " : "") +
				(statuss != null ? "status IN (:status) AND " : "") +
				(numCheque != null ? "cheque.numCheque LIKE :numCheque AND " : "") +
				pesqPorModo +
				"categoria.codigoSuper = :entradaOuSaida", params, false);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Movimentacao> findBy(Date dtMoviment, BigDecimal valorTotal, Carteira carteira,
			BandeiraCartao bandeiraCartao,
			Categoria categoria)
			throws ViewException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtMoviment", dtMoviment);
		params.put("valorTotal", valorTotal);
		params.put("carteira", carteira);
		params.put("bandeiraCartao", bandeiraCartao);
		params.put("categoria", categoria);

		return getThiz().findResults("FROM Movimentacao " +
				"WHERE " +
				"dtMoviment = :dtMoviment AND " +
				"valorTotal = :valorTotal AND " +
				"carteira = :carteira AND " +
				"categoria = :categoria AND " +
				"bandeiraCartao = :bandeiraCartao", params, false);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Movimentacao> findBy(Date dtMoviment, BigDecimal valorTotal, Carteira carteira, Carteira carteiraDestino,
			BandeiraCartao bandeiraCartao,
			Categoria categoria)
					throws ViewException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtMoviment", dtMoviment);
		params.put("valorTotal", valorTotal);
		params.put("carteira", carteira);
		params.put("carteiraDestino", carteiraDestino);
		params.put("bandeiraCartao", bandeiraCartao);
		params.put("categoria", categoria);
		
		return getThiz().findResults("FROM Movimentacao " +
				"WHERE " +
				"dtMoviment = :dtMoviment AND " +
				"valorTotal = :valorTotal AND " +
				"carteira = :carteira AND " +
				"carteiraDestino = :carteiraDestino AND " +
				"categoria = :categoria AND " +
				"bandeiraCartao = :bandeiraCartao", params, false);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Movimentacao findBy(BigDecimal valorTotal, Carteira carteira, String numChequeProprio)
			throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valorTotal", valorTotal);
		params.put("carteira", carteira);
		params.put("numChequeProprio", numChequeProprio);

		return getThiz().findSingleResult("FROM Movimentacao m " +
				"WHERE " +
				"m.valorTotal = :valorTotal AND " +
				"m.carteira = :carteira AND " +
				"m.cheque.numCheque = :numChequeProprio", params, false);

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Movimentacao> findAnterioresExtrato(Date dtIni, Carteira carteira) throws ViewException {
		dtIni = CalendarUtil.zeroDate(dtIni);
		final String jpql = "FROM Movimentacao WHERE carteira = :carteira AND "
				+
				"((status IN ('ABERTA', 'A_COMPENSAR') AND dtVenctoEfetiva < :dtIni) OR "
				+
				"(status NOT IN ('ABERTA', 'A_COMPENSAR') AND dtVenctoEfetiva <= :dtIni AND dtPagto > :dtIni)) " +
				"ORDER BY dtVenctoEfetiva, categoria.codigoSuper, valorTotal";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtIni", dtIni);
		params.put("carteira", carteira);
		return getThiz().findResults(jpql, params, false);

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Movimentacao> findAbertasAnteriores(Date dtIni, List<Carteira> carteiras) throws ViewException {
		dtIni = CalendarUtil.zeroDate(dtIni);
		final String jpql = "FROM Movimentacao WHERE " + 
				"categoria.codigoSuper = 2 AND " +
				"carteira IN :carteiras AND " +
				"status IN ('ABERTA', 'A_COMPENSAR') AND dtVenctoEfetiva < :dtIni " +
				"ORDER BY dtVenctoEfetiva, categoria.codigoSuper, valorTotal";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtIni", dtIni);
		params.put("carteiras", carteiras);
		return getThiz().findResults(jpql, params, false);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<String> findDescricaoBy(Carteira carteira, String descricaoIni) throws ViewException {
		try {
			final String jpql = "SELECT distinct m.descricao FROM Movimentacao m WHERE carteira = :carteira AND m.descricao LIKE :descricaoIni ORDER BY m.descricao";
			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("carteira", carteira);
			qry.setParameter("descricaoIni", descricaoIni + "%");
			return qry.getResultList();
		} catch (Exception e) {
			throw new ViewException("Erro ao pesquisar descrições", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Movimentacao> findBy(Carteira carteira, Date dtPagto, Modo modo, Categoria categoria)
			throws ViewException {
		try {
			final String jpql = "FROM Movimentacao m WHERE m.carteira = :carteira AND m.modo = :modo AND m.dtPagto = :dtPagto AND m.categoria = :categoria";
			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("carteira", carteira);
			qry.setParameter("modo", modo);
			qry.setParameter("dtPagto", dtPagto);
			qry.setParameter("categoria", categoria);
			return qry.getResultList();
		} catch (Exception e) {
			throw new ViewException("Erro ao pesquisar movimentações.", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotal(Categoria categoria, Date dtIni, Date dtFim) throws ViewException {
		return getThiz().findTotal(categoria, dtIni, dtFim, false, false, null);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotal(Categoria categoria, Date dtIni, Date dtFim, boolean incluirSubCategs,
			boolean somenteTotalizaveis, String compararPorQualCampoDt)
			throws ViewException {
		// nos casos de totalização para 199 e 299 é necessário comparar pela dt_moviment, pelos casos onde a 299 acontece dia(s) depois da 199
		if ((compararPorQualCampoDt == null) || "".equals(compararPorQualCampoDt)) {
			compararPorQualCampoDt = "dt_util";
		}

		String jpql;

		jpql = "SELECT SUM( m.valor_total ) FROM " +
				"fin_movimentacao m, fin_categoria c " +
				"WHERE " +
				"m.categoria_id = c.id AND " +
				"m." + compararPorQualCampoDt + " BETWEEN :dtIni AND :dtFim AND ";

		if (incluirSubCategs) {
			jpql += " c.codigo LIKE :codigoInicial";
		} else {
			jpql += " c.id = :categoriaId";
		}

		if (somenteTotalizaveis) {
			jpql += " AND totalizavel = true";
		}

		try {
			final Query qry = getEntityManager().createNativeQuery(jpql);
			qry.setParameter("dtIni", dtIni);
			qry.setParameter("dtFim", dtFim);

			if (incluirSubCategs) {
				qry.setParameter("codigoInicial", categoria.getCodigo() + "%");
			} else {
				qry.setParameter("categoriaId", categoria.getId());

			}

			BigDecimal r = (BigDecimal) qry.getSingleResult();
			if (r == null) {
				r = BigDecimal.ZERO;
			}

			return r;
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotal(Carteira carteira, Categoria categoria, OperadoraCartao operadoraCartao, Modo modo,
			Date dtIni, Date dtFim) throws ViewException {

		String pesqPorCarteira = "";
		if (carteira != null) {
			pesqPorCarteira = "m.carteira_id = :carteiraId AND ";
		}
		String pesqPorCategoria = "";
		if (categoria != null) {
			pesqPorCategoria = "m.categoria_id = :categoriaId AND ";
		}
		String pesqPorOperadoraCartao = "";
		if (operadoraCartao != null) {
			pesqPorOperadoraCartao = "m.operadora_cartao_id = :operadoraCartaoId AND ";
		}
		String pesqPorModo = "";
		if (modo != null) {
			pesqPorModo = "m.modo_id = :modoId AND ";
		}

		String jpql = "SELECT SUM( m.valor_total ) FROM " +
				"fin_movimentacao m " +
				"WHERE " +
				pesqPorCarteira +
				pesqPorCategoria +
				pesqPorOperadoraCartao +
				pesqPorModo +
				"m.dt_pagto BETWEEN :dtIni AND :dtFim";

		try {
			final Query qry = getEntityManager().createNativeQuery(jpql);
			if (carteira != null) {
				qry.setParameter("carteiraId", carteira.getId());
			}
			if (categoria != null) {
				qry.setParameter("categoriaId", categoria.getId());
			}
			if (operadoraCartao != null) {
				qry.setParameter("operadoraCartaoId", operadoraCartao.getId());
			}
			if (modo != null) {
				qry.setParameter("modoId", modo.getId());
			}
			qry.setParameter("dtIni", dtIni);
			qry.setParameter("dtFim", dtFim);

			return (BigDecimal) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findTotal(Carteira carteira, Categoria categoria, Date dtIni, Date dtFim) throws ViewException {
		String jpql = "SELECT SUM( m.valor_total ) FROM " +
				"fin_movimentacao m " +
				"WHERE " +
				"m.carteira_id = :carteiraId AND " +
				"m.categoria_id = :categoriaId AND " +
				"m.dt_pagto BETWEEN :dtIni AND :dtFim";

		try {
			final Query qry = getEntityManager().createNativeQuery(jpql);
			qry.setParameter("carteiraId", carteira.getId());
			qry.setParameter("categoriaId", categoria.getId());
			qry.setParameter("dtIni", dtIni);
			qry.setParameter("dtFim", dtFim);

			return (BigDecimal) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Movimentacao findBy(Cadeia cadeia, Categoria categoria) throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cadeia", cadeia);

		String sql = "FROM Movimentacao WHERE cadeia = :cadeia";

		if (categoria != null) {
			sql += " AND categoria = :categoria";
			params.put("categoria", categoria);
		}

		return getThiz().findSingleResult(sql, params, false);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Movimentacao> findBy(Cadeia cadeia) throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cadeia", cadeia);
		String sql = "FROM Movimentacao WHERE cadeia = :cadeia";
		return getThiz().findResults(sql, params, false);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public Movimentacao findBy(Cadeia cadeia, Integer cadeiaOrdem) throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cadeia", cadeia);
		params.put("cadeiaOrdem", cadeiaOrdem);

		return getThiz().findSingleResult("FROM Movimentacao " +
				"WHERE " +
				"cadeia = :cadeia AND " +
				"cadeiaOrdem = :cadeiaOrdem", params);
	}

	@Override
	public MovimentacaoFinder getThiz() {
		return (MovimentacaoFinder) super.getThiz();
	}

	/**
	 * Lista erros de movimentações 1.99 e 2.99.
	 */
	@Override
	public List<Movimentacao> listarErrosTransferenciaEntreCarteiras(Date dtIni, Date dtFim) throws ViewException {

		// pesquisa todas as 199
		String sql = "SELECT " +
				"m.id, " +
				"m.cadeia_id, " +
				"m.descricao, " +
				"m.tipo_lancto, " +
				"m.dt_vencto, " +
				"m.dt_vencto_efetiva, " +
				"m.dt_pagto, " +
				"m.dt_util, " +
				"m.valor, " +
				"m.valor_total, " +
				"m.carteira_id, " +
				"c.descricao as carteira_descricao, " +
				"categ.id categoria_id, " +
				"categ.descricao as categoria_descricao, " +
				"categ.codigo as categoria_codigo " +
				"FROM " +
				"fin_movimentacao m, fin_carteira c, fin_categoria categ " +
				"WHERE " +
				"m.carteira_id = c.id AND " +
				"m.categoria_id = categ.id AND " +
				"m.categoria_id IN (6,7) AND " +
				"m.dt_pagto BETWEEN :dtIni AND :dtFim " +
				"ORDER BY m.cadeia_id, m.dt_pagto, m.valor";

		Query sqlQry = getEntityManager().createNativeQuery(sql);
		sqlQry.setParameter("dtIni", dtIni);
		sqlQry.setParameter("dtFim", dtFim);
		@SuppressWarnings("rawtypes")
		List movs = sqlQry.getResultList();

		List<Movimentacao> results = new ArrayList<Movimentacao>();

		List<Long> jaIncluidas = new ArrayList<Long>();

		// Percorre todas as 199 e 299
		for (Object m : movs) {

			BigInteger movId = (BigInteger) ((Object[]) m)[0];

			if (jaIncluidas.contains(movId.longValue()))
				continue;

			BigInteger movCadeiaId = (BigInteger) ((Object[]) m)[1];
			
			// Se não tiver cadeia, informa como erro
			if (movCadeiaId == null) {
				Movimentacao mov = getThiz().findById(movId.longValue());
				results.add(mov);
				jaIncluidas.add(mov.getId());
				continue;
			}
			
			BigDecimal movValorTotal = (BigDecimal) ((Object[]) m)[9];
			BigInteger movCategoriaCodigo = (BigInteger) ((Object[]) m)[14];
			
			List<Movimentacao> movsCadeia = getThiz().findBy(getThiz().findById(movId.longValue()).getCadeia());
			
			// Se a cadeia tiver menos de 2 movimentações, informa como erro
			if (movsCadeia.size() < 2) {
				Movimentacao mov = getThiz().findById(movId.longValue());
				results.add(mov);
				jaIncluidas.add(mov.getId());
				continue;
			}

			// Procura o par
			Integer parCategoriaCodigo = movCategoriaCodigo.intValue() == 199 ? 299 : 199;
			sql = "SELECT m.id, m.valor_total FROM fin_movimentacao m, fin_categoria c WHERE m.categoria_id = c.id AND c.codigo = :parCategoriaCodigo AND m.cadeia_id = :cadeiaId";
			sqlQry = getEntityManager().createNativeQuery(sql);
			sqlQry.setParameter("cadeiaId", movCadeiaId);
			sqlQry.setParameter("parCategoriaCodigo", parCategoriaCodigo);

			Object[] mPar = null;

			try {
				mPar = (Object[]) sqlQry.getSingleResult();
			} catch (Exception e) {
				throw new ViewException("Erro ao pesquisar o par correspondente", e);
			}

			// Se não achou a 299, também é um erro
			if (mPar == null) {
				Movimentacao mov = getThiz().findById(movId.longValue());
				results.add(mov);
				jaIncluidas.add(mov.getId());
				continue;
			}

			BigInteger mParId = (BigInteger) mPar[0];

			BigDecimal valorTotalPar = (BigDecimal) ((Object[]) mPar)[1];

			if (valorTotalPar.compareTo(movValorTotal) != 0) {
				Movimentacao mov = getThiz().findById(movId.longValue());
				results.add(mov);
				Movimentacao movPar = getThiz().findById(mParId.longValue());
				results.add(movPar);
				jaIncluidas.add(mov.getId());
				jaIncluidas.add(movPar.getId());
			}

		}

		return results;

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<Movimentacao> findBy(Carteira carteira, Categoria categoria, Date dtUtilIni, Date dtUtilFim)
			throws ViewException {
		final String jpql = "FROM Movimentacao WHERE carteira = :carteira AND categoria = :categoria AND dtUtil BETWEEN :dtUtilIni AND :dtUtilFim";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("carteira", carteira);
		params.put("categoria", categoria);
		params.put("dtUtilIni", dtUtilIni);
		params.put("dtUtilFim", dtUtilFim);
		return getThiz().findResults(jpql, params);
	}


}
