package com.bonsucesso.erp.estoque.data;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoSaldo;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;
import com.ocabit.utils.calendar.CalendarUtil;
import com.ocabit.utils.strings.StringUtils;


/**
 * Implementação de Finder para entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("produtoFinder")
public class ProdutoFinderImpl extends BasicEntityFinderImpl<Produto> implements ProdutoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -8428094764335314685L;
	protected static Logger logger = Logger.getLogger(ProdutoFinderImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Produto> findByStr(String str) throws ViewException {
		try {
			final String jpql = "FROM Produto WHERE " +
					"descricao LIKE :str OR " +
					"marca.marca LIKE :str OR " +
					"fornecedor.pessoa.nomeFantasia LIKE :str OR " +
					"fornecedor.pessoa.nome LIKE :str";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("str", "%" + str + "%");
			return qry.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar produto.", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Produto findByReduzido(Long reduzido) throws ViewException {
		final String jpql = "FROM Produto WHERE reduzido = :reduzido";
		return getThiz().findSingleResult(jpql, "reduzido", reduzido);
	}

	/**
	 * Se não passar o dtImportacao, retorna todos. Se passar, deve retornar apenas 1.
	 * 
	 * @param reduzidoEkt
	 * @param dtImportacao
	 * @return List<Produto> produtos
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Produto> findByReduzidoEkt(Integer reduzidoEkt, Date dtImportacao) throws ViewException {
		String jpql = "FROM Produto WHERE reduzidoEkt = :reduzidoEkt ";

		Map<String, Object> params = new HashMap<String, Object>();

		if (dtImportacao != null) {
			jpql += "AND (reduzidoEktDesde <= :dtImportacao OR reduzidoEktDesde IS NULL) AND (reduzidoEktAte >= :dtImportacao OR reduzidoEktAte IS NULL) ";
			params.put("dtImportacao", dtImportacao);
		}
		jpql += "ORDER BY reduzidoEktDesde";
		params.put("reduzidoEkt", reduzidoEkt);

		List<Produto> l = getThiz().findResults(jpql, params, false);

		if (dtImportacao != null && l != null && l.size() > 1) {
			throw new ViewException("Mais de um produto com o mesmo reduzido (" + reduzidoEkt + ") no período ("
					+ new SimpleDateFormat("dd/MM/yyyy").format(dtImportacao) + ")");
		} else {
			return l;
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Produto findProximo(Produto produto) throws ViewException {
		final String jpql = "FROM Produto WHERE reduzido > :reduzido ORDER BY reduzido";
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setMaxResults(1);
			JPAUtil.cacheOn(qry);
			qry.setParameter("reduzido", produto.getReduzido());
			return (Produto) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Produto findAnterior(Produto produto) throws ViewException {
		final String jpql = "FROM Produto WHERE reduzido < :reduzido ORDER BY reduzido DESC LIMIT 1";
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setMaxResults(1);
			JPAUtil.cacheOn(qry);
			qry.setParameter("reduzido", produto.getReduzido());
			return (Produto) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ProdutoSaldo findSaldo(Produto produto, String tamanho) throws ViewException {
		final String jpql = "FROM ProdutoSaldo WHERE produto = :produto AND gradeTamanho.grade = :grade AND gradeTamanho.tamanho = :tamanho";
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("produto", produto);
			qry.setParameter("grade", produto.getGrade());
			qry.setParameter("tamanho", tamanho);
			return (ProdutoSaldo) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public BigDecimal findQtdeEmEstoqueBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException {
		final String jpql = "SELECT SUM(ps.qtde) FROM ProdutoSaldo ps "
				+ "WHERE "
				+ "ps.qtde > 0 AND "
				+ "ps.produto.fornecedor = :fornecedor AND "
				+ "ps.produto.subdepto = :subdepto";
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("fornecedor", fornecedor);
			qry.setParameter("subdepto", subdepto);
			return (BigDecimal) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List findProdutosSemReduzidoEktAteNull() throws ViewException {

		// Encontro todos os produtos que sejam do mesmo REDUZIDO_EKT e que nenhum deles tenha reduzido_ekt_ate = NULL
		final String sql = "SELECT q1.reduzido_ekt FROM " +
				"( " +
				"SELECT " +
				"reduzido_ekt, " +
				"count(*) as qt " +
				"FROM est_produto " +
				"GROUP BY reduzido_ekt ORDER BY reduzido_ekt) q1, " +
				"( " +
				"SELECT " +
				"reduzido_ekt, " +
				"count(*) as qt " +
				"FROM est_produto " +
				"WHERE " +
				"reduzido_ekt_ate IS NOT NULL " +
				"GROUP BY reduzido_ekt ORDER BY reduzido_ekt " +
				") q2 " +
				"WHERE " +
				"q1.reduzido_ekt = q2.reduzido_ekt AND q1.qt = q2.qt";

		try {
			final Query qry = getEntityManager().createNativeQuery(sql);
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
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Long findProximoReduzido() throws ViewException {
		try {
			final String sql = "SELECT max(reduzido)+1 FROM est_produto WHERE reduzido > 9999000000";
			final Query qry = getEntityManager().createNativeQuery(sql);
			BigInteger proximoReduzido = (BigInteger) qry.getSingleResult() != null ? (BigInteger) qry.getSingleResult()
					: new BigInteger("9999000001");
			return proximoReduzido.longValue();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar próximo reduzido.", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String findProximaReferencia(Produto p) throws ViewException {
		try {
			if (p.getFornecedor() == null || p.getFornecedor().getCodigo() == null ||
					p.getSubdepto() == null || p.getSubdepto().getCodigo() == null) {
				throw new ViewException("Fornecedor e Subdepto do produto devem ser informados para gerar a próxima referência.");
			}

			final String sql = "SELECT max(p.referencia) FROM est_produto p WHERE p.fornecedor_id = :fornecedor_id AND p.subdepto_id = :subdepto_id AND produto_eh_atual(p.id) = 'S'";
			final Query qry = getEntityManager().createNativeQuery(sql);

			qry.setParameter("fornecedor_id", p.getFornecedor().getId());
			qry.setParameter("subdepto_id", p.getSubdepto().getId());

			String ultimaReferencia = (String) qry.getSingleResult();

			String proximaReferencia;

			if (ultimaReferencia == null) {
				proximaReferencia = StringUtils.zerofill(p.getSubdepto().getCodigo().toString(), 3) + "001";
			} else {
				Long lProximaReferencia = Long.parseLong(ultimaReferencia);
				lProximaReferencia++;
				proximaReferencia = StringUtils.zerofill(lProximaReferencia.toString(), 6);
			}

			return proximaReferencia;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar próximo reduzido.", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findQtdeBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException {
		try {
			String jpql = "SELECT sum(ps.qtde) FROM ProdutoSaldo ps WHERE ps.produto.fornecedor = :fornecedor AND ps.produto.subdepto = :subdepto "
					// TODO: considerando que atual é...
					+ "AND ps.produto.reduzidoEktAte IS NULL";

			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("fornecedor", fornecedor);
			qry.setParameter("subdepto", subdepto);
			return (BigDecimal) qry.getSingleResult();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar qtde total de itens por fornecedor/subdepto", e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findQtdeBy(Produto produto) throws ViewException {
		try {
			String jpql = "SELECT sum(ps.qtde) FROM ProdutoSaldo ps WHERE ps.produto = :produto "
					// TODO: considerando que atual é...
					+ "AND ps.produto.reduzidoEktAte IS NULL";

			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("produto", produto);
			return (BigDecimal) qry.getSingleResult();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar qtde total de itens por fornecedor/subdepto", e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Produto> findAtuaisBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException {
		try {
			String jpql = "FROM Produto p WHERE p.fornecedor = :fornecedor AND p.subdepto = :subdepto "
					+ "AND p.reduzidoEktAte IS NULL";

			Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("fornecedor", fornecedor);
			qry.setParameter("subdepto", subdepto);
			return qry.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Produto> findNaoArtigosCortinaBy(String str, List<Subdepartamento> subdeptos) throws ViewException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		String jpql = "SELECT p FROM "
				+ "Produto p "
				+ "LEFT JOIN ArtigoCortina ac ON p.id = ac.produto.id "
				+ "WHERE "
				+ "p.reduzidoEktAte IS NULL AND "
				+ "p.subdepto IN (:subdeptos) AND "
				+ "ac.id IS NULL";
		if (str != null) {
			str = str.toUpperCase();
			jpql += " AND (str(p.reduzido) LIKE :reduzido OR p.descricao LIKE :descricao)";
			params.put("reduzido", "%" + str);
			params.put("descricao", "%" + str + "%");
		}
		
		params.put("subdeptos", subdeptos);
		
		return getThiz().findResults(jpql, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Produto> findProdutosAtivos() throws ViewException {

		// Pega todos os produtos que tenham saldo positivo ou que a última venda tenha sido feita a menos de um ano
		
		String sql = "SELECT " +
							"p.* " +
						"FROM " +
							"est_produto p JOIN " + 
							"(SELECT produto_id, SUM(qtde) as total FROM est_produto_saldo WHERE qtde > 0 GROUP BY produto_id) s " + 
						    	"ON p.id = s.produto_id " +
						"WHERE s.total > 0 OR p.dt_ult_venda >= :dtIni";
		
		
		try {
			final Query qry = getEntityManager().createNativeQuery(sql,Produto.class);
			
			Date umAnoAtras = CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(new Date(), -12));
			qry.setParameter("dtIni", umAnoAtras);
			
			return qry.getResultList();

		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Object[]> findFornecedoresSubdeptosDeProdutosAtivos() throws ViewException {

		// Pega todos os produtos que tenham saldo positivo ou que a última venda tenha sido feita a menos de um ano

		String sql = "SELECT " +
				"p.fornecedor_id, " +
				"p.subdepto_id " +
				"FROM " +
				"est_produto p JOIN " +
				"(SELECT produto_id, SUM(qtde) as total FROM est_produto_saldo WHERE qtde > 0 GROUP BY produto_id) s " +
				"	ON p.id = s.produto_id " +
				"WHERE s.total > 0 OR p.dt_ult_venda >= :dtIni " +
				"GROUP BY p.fornecedor_id, p.subdepto_id ORDER BY p.fornecedor_id, p.subdepto_id ";

		try {
			final Query qry = getEntityManager().createNativeQuery(sql);

			Date umAnoAtras = CalendarUtil.primeiroDiaNoMesAno(CalendarUtil.incMes(new Date(), -12));

			qry.setParameter("dtIni", umAnoAtras);
			List l = qry.getResultList();
			
			return l;

		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
	}
	
	
	
	
	

}
