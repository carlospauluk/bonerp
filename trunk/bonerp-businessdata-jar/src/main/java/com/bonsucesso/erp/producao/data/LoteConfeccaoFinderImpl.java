package com.bonsucesso.erp.producao.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.model.Confeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccao;
import com.bonsucesso.erp.producao.model.LoteConfeccaoItem;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade LoteConfeccao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("loteConfeccaoFinder")
public class LoteConfeccaoFinderImpl extends BasicEntityFinderImpl<LoteConfeccao> implements LoteConfeccaoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 1975267810975260195L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public LoteConfeccaoItem findBy(Integer loteCodigo, Integer instituicaoCodigo, Integer tipoArtigoCodigo)
			throws ViewException {
		final String jpql = "FROM LoteConfeccaoItem i " +
				"WHERE i.loteConfeccao.codigo = :loteCodigo AND " +
				"i.confeccao.instituicao.codigo = :instituicaoCodigo AND " +
				"i.confeccao.tipoArtigo.codigo = :tipoArtigoCodigo";

		try {
			final Query qry = getEntityManager().createQuery(jpql);

			qry.setParameter("loteCodigo", loteCodigo);
			qry.setParameter("instituicaoCodigo", instituicaoCodigo);
			qry.setParameter("tipoArtigoCodigo", tipoArtigoCodigo);

			return (LoteConfeccaoItem) qry.getSingleResult();
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
	public LoteConfeccao findBy(Integer codigo) throws ViewException {
		final String jpql = "FROM LoteConfeccao c WHERE c.codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Integer findNextCodigo() throws ViewException {
		final String jpql = "SELECT max(codigo)+1 FROM LoteConfeccao";

		try {
			final Query qry = getEntityManager().createQuery(jpql);
			return (Integer) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<LoteConfeccao> findComConfeccao(Confeccao confeccao) {
		String jpql = "SELECT distinct(lci.loteConfeccao) FROM LoteConfeccaoItem lci WHERE lci.confeccao = :confeccao";
		Query qry = getThiz().getEntityManager().createQuery(jpql);
		qry.setParameter("confeccao", confeccao);
		return qry.getResultList();
	}

}
