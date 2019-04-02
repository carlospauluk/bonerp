package com.bonsucesso.erp.rh.data;



import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.rh.model.AvaliacaoVendaResp;
import com.bonsucesso.erp.rh.model.AvaliacaoVendaRespQuestao;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade AvaliacaoVendaResp.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Repository("avaliacaoVendaRespFinder")
public class AvaliacaoVendaRespFinderImpl extends BasicEntityFinderImpl<AvaliacaoVendaResp> implements
		AvaliacaoVendaRespFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 4415165730198318893L;

	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			RuntimeException.class, ViewException.class }, readOnly = true)
	@Override
	public AvaliacaoVendaRespQuestao findBy(AvaliacaoVendaResp avaliacaoVendaResp, Integer ordem) throws ViewException {

		try {
			final Query qry = getEntityManager()
					.createQuery("FROM AvaliacaoVendaRespQuestao WHERE avaliacaoVendaResp = :avaliacaoVendaResp AND avaliacaoVendaQuestao.ordem = :ordem");
			qry.setParameter("avaliacaoVendaResp", avaliacaoVendaResp);
			qry.setParameter("ordem", ordem);

			return (AvaliacaoVendaRespQuestao) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único (" + getGenericType().getSimpleName() + ")", e);
		}

	}

}
