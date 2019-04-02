package com.bonsucesso.erp.producao.data;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.ConfeccaoItem;
import com.bonsucesso.erp.producao.model.ConfeccaoPreco;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;


/**
 * Implementação de Finder para entidade Confeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("confeccaoFinder")
public class ConfeccaoFinderImpl extends BasicEntityFinderImpl<Confeccao> implements ConfeccaoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 3402195415316567140L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ConfeccaoItem findItemBy(Instituicao instituicao, TipoArtigo tipoArtigo, Insumo insumo,
			GradeTamanho gradeTamanho) throws ViewException {
		final String jpql = "FROM ConfeccaoItem WHERE confeccao.instituicao = :instituicao AND confeccao.tipoArtigo = :tipoArtigo AND insumo = :insumo AND gradeTamanho = :gradeTamanho";

		try {
			final Query qry = getEntityManager().createQuery(jpql);

			qry.setParameter("instituicao", instituicao);
			qry.setParameter("tipoArtigo", tipoArtigo);
			qry.setParameter("insumo", insumo);
			qry.setParameter("gradeTamanho", gradeTamanho);

			return (ConfeccaoItem) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Confeccao> findBy(Instituicao instituicao, TipoArtigo tipoArtigo) throws ViewException {
		final String jpql = "FROM Confeccao WHERE instituicao = :instituicao AND tipoArtigo = :tipoArtigo";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("instituicao", instituicao);
		params.put("tipoArtigo", tipoArtigo);
		return getThiz().findResults(jpql, params);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Confeccao> findBy(Instituicao instituicao, TipoArtigo tipoArtigo, Boolean ocultas) throws ViewException {
		String jpql = "FROM Confeccao WHERE instituicao = :instituicao AND tipoArtigo = :tipoArtigo";
		// A lógica é a seguinte: pode não exibir as ocultas ou exibir todas.
		if (ocultas == false) {
			jpql += " AND oculta = false";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("instituicao", instituicao);
		params.put("tipoArtigo", tipoArtigo);
		return getThiz().findResults(jpql, params);
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Confeccao> findBy(Instituicao instituicao) throws ViewException {
		final String jpql = "FROM Confeccao WHERE instituicao = :instituicao";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("instituicao", instituicao);
		return getThiz().findResults(jpql, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<ConfeccaoItem> findConfeccaoItemBy(Confeccao confeccao, TipoInsumo tipoInsumo) throws ViewException {
		try {
			final String jpql = "FROM ConfeccaoItem WHERE confeccao = :confeccao AND insumo.tipoInsumo = :tipoInsumo ORDER BY insumo.descricao";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("confeccao", confeccao);
			qry.setParameter("tipoInsumo", tipoInsumo);

			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}

	}

	/**
	 * Usado somente na importação (depois pode remover).
	 */
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ConfeccaoItem findConfeccaoItemBy(Integer instituicaoCodigo, Integer tipoArtigoCodigo, Integer insumoCodigo)
			throws ViewException {
		try {
			final String jpql = "FROM ConfeccaoItem WHERE confeccao.instituicao.codigo = :instituicaoCodigo AND confeccao.tipoArtigo.codigo = :tipoArtigoCodigo AND insumo.codigo = :insumoCodigo";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("instituicaoCodigo", instituicaoCodigo);
			qry.setParameter("tipoArtigoCodigo", tipoArtigoCodigo);
			qry.setParameter("insumoCodigo", insumoCodigo);
			JPAUtil.cacheOn(qry);
			return (ConfeccaoItem) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}

	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Confeccao> findBy(String str) throws ViewException {
		StringBuilder jpql = new StringBuilder("FROM Confeccao WHERE ");
		String[] partes = str.split(" ");

		for (int i = 0; i < partes.length; i++) {
			jpql.append("(instituicao.nome LIKE :parte" + i +
					" OR tipoArtigo.descricao LIKE :parte" + i + " OR descricao LIKE :parte" + i + ") AND ");
		}
		jpql.append(" 1=1"); // rta: ao invés de remover o último OR, adiciono uma condição "false"
		jpql.append(" ORDER BY descricao"); // rta: ao invés de remover o último OR, adiciono uma condição "false"
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < partes.length; i++) {
			params.put("parte" + i, "%" + partes[i] + "%");
		}
		return getThiz().findResults(jpql.toString(), params);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Confeccao findBy(Instituicao instituicao, TipoArtigo tipoArtigo, String descricao) throws ViewException {
		final String jpql = "FROM Confeccao WHERE instituicao = :instituicao AND tipoArtigo = :tipoArtigo AND descricao LIKE :descricao";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("instituicao", instituicao);
		params.put("tipoArtigo", tipoArtigo);
		params.put("descricao", descricao);
		return getThiz().findSingleResult(jpql, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<ConfeccaoPreco> findConfeccaoPrecosByStr(String str) throws ViewException {
		try {
			final String jpql = "FROM ConfeccaoPreco cp WHERE cp.descricao LIKE :str";
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("str", "%" + str + "%");
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Confeccao> findBy(Insumo insumo) throws ViewException {
		final String jpql = "SELECT distinct(ci.confeccao) FROM ConfeccaoItem ci WHERE ci.insumo = :insumo";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("insumo", insumo);
		return getThiz().findResults(jpql, params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<LoteConfeccao> findLotesConfeccaoBy(Confeccao confeccao) throws ViewException {
		final String sql = "SELECT l.*  FROM prod_lote_confeccao l WHERE id IN (SELECT lote_confeccao_id FROM prod_lote_confeccao_item WHERE confeccao_id = :confeccaoId)";
		try {
			final Query qry = getEntityManager().createNativeQuery(sql, LoteConfeccao.class);
			qry.setParameter("confeccaoId", confeccao.getId());
			return qry.getResultList();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		}
	}

}
