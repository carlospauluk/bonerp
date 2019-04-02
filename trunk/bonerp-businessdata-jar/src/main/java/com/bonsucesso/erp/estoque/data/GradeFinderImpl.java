package com.bonsucesso.erp.estoque.data;



import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Grade;
import com.bonsucesso.erp.estoque.model.GradeTamanho;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;


/**
 * Implementação de Finder para entidade Grade.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("gradeFinder")
public class GradeFinderImpl extends BasicEntityFinderImpl<Grade> implements GradeFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -6251282604161920819L;

	protected static Logger logger = Logger.getLogger(GradeFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Grade findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM Grade WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GradeTamanho findByGradeAndOrdem(Grade grade, Integer ordem) throws ViewException {
		try {
			final String jpql = "FROM GradeTamanho WHERE grade = :grade AND ordem = :ordem";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("grade", grade);
			params.put("ordem", ordem);
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("grade", grade);
			qry.setParameter("ordem", ordem);
			return (GradeTamanho) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao pesquisar Grade/Tamanho", e);
		}
	}
	
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GradeTamanho findByGradeAndPosicao(Grade grade, Integer posicao) throws ViewException {
		try {
			final String jpql = "FROM GradeTamanho WHERE grade = :grade AND posicao = :posicao";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("grade", grade);
			params.put("posicao", posicao);
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("grade", grade);
			qry.setParameter("posicao", posicao);
			return (GradeTamanho) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao pesquisar Grade/Tamanho", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GradeTamanho findGradeTamanhoById(Long id) throws ViewException {
		final String jpql = "FROM GradeTamanho WHERE id = :id";
		StringBuilder campos = new StringBuilder("");
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("id", id);
			return (GradeTamanho) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + " por "
					+ campos.toString() + ")", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GradeTamanho findByCodigoGradeAndTamanho(Integer codigoGrade, String tamanho) throws ViewException {
		final String jpql = "FROM GradeTamanho WHERE grade.codigo = :codigoGrade AND tamanho = :tamanho";
		StringBuilder campos = new StringBuilder("");
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("codigoGrade", codigoGrade);
			qry.setParameter("tamanho", tamanho);
			return (GradeTamanho) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + " por "
					+ campos.toString() + ")", e);
		}
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	@Cacheable(value = "methodsCache")
	public GradeTamanho findByCodigoGradeAndOrdem(Integer codigoGrade, Integer ordem) throws ViewException {
		logger.debug("findByCodigoGradeAndOrdem(Integer codigoGrade = '" + codigoGrade + "', Integer ordem = '" + ordem + "')");
		final String jpql = "FROM GradeTamanho WHERE grade.codigo = :codigoGrade AND ordem = :ordem";
		StringBuilder campos = new StringBuilder("");
		try {
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("codigoGrade", codigoGrade);
			qry.setParameter("ordem", ordem);
			logger.debug("qry.getSingleResult();");
			GradeTamanho gt = (GradeTamanho) qry.getSingleResult();
			logger.debug("gt = '" + gt.getId() + "'");
			logger.debug("OK");
			return gt;
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + " por "
					+ campos.toString() + ")", e);
		}
	}

}
