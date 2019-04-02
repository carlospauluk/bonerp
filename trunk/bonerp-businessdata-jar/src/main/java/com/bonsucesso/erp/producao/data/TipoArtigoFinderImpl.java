package com.bonsucesso.erp.producao.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.producao.model.Instituicao;
import com.bonsucesso.erp.producao.model.TipoArtigo;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade TipoArtigo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("tipoArtigoFinder")
public class TipoArtigoFinderImpl extends BasicEntityFinderImpl<TipoArtigo> implements TipoArtigoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 4902597884486782870L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public TipoArtigo findByCodigo(Integer codigo) throws ViewException {
		final String jpql = "FROM TipoArtigo WHERE codigo = :codigo";
		return getThiz().findSingleResult(jpql, "codigo", codigo);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<TipoArtigo> findByInstituicao(Instituicao instituicao) throws ViewException {
		final String jpql = "SELECT distinct(c.tipoArtigo) FROM Confeccao c WHERE c.instituicao = :instituicao ORDER BY c.tipoArtigo.descricao";
		return getThiz().findResults(jpql, "instituicao", instituicao);
	}

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Integer findNextCodigo() throws ViewException {
		final String jpql = "SELECT max(codigo)+1 FROM TipoArtigo";

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

}
