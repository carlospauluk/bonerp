package com.bonsucesso.erp.rh.data;



import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.rh.model.AvaliacaoVenda;
import com.bonsucesso.erp.rh.model.AvaliacaoVendaQuestao;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;


/**
 * Implementação de Finder para entidade AvaliacaoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("avaliacaoVendaFinder")
public class AvaliacaoVendaFinderImpl extends BasicEntityFinderImpl<AvaliacaoVenda> implements AvaliacaoVendaFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 4275096181387977263L;

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class }, readOnly = true)
	@Override
	public AvaliacaoVendaQuestao findBy(AvaliacaoVenda avaliacaoVenda, Integer ordem) throws ViewException {

		try {
			final Query qry = getEntityManager()
					.createQuery("FROM AvaliacaoVendaQuestao WHERE avaliacaoVenda = :avaliacaoVenda AND ordem = :ordem");
			JPAUtil.cacheOn(qry);
			qry.setParameter("avaliacaoVenda", avaliacaoVenda);
			qry.setParameter("ordem", ordem);

			return (AvaliacaoVendaQuestao) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}

	}

}
