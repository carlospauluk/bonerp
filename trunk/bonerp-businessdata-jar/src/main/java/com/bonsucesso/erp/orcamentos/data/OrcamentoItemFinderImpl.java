package com.bonsucesso.erp.orcamentos.data;



import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.orcamentos.model.OrcamentoGrupoItem;
import com.bonsucesso.erp.orcamentos.model.OrcamentoItem;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade OrcamentoItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("orcamentoItemFinder")
public class OrcamentoItemFinderImpl extends BasicEntityFinderImpl<OrcamentoItem> implements OrcamentoItemFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = 6137570928852757357L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Integer findProximaOrdem(OrcamentoGrupoItem grupo) throws ViewException {
		if ((grupo.getItens() == null) || (grupo.getItens().size() == 0)) {
			return 1;
		}
		final String jpql = "SELECT max(ordem) FROM OrcamentoItem WHERE grupo = :grupo";

		try {
			final Query qry = getEntityManager().createQuery(jpql, Integer.class);
			qry.setParameter("grupo", grupo);
			Integer ultimaOrdem = (Integer) qry.getSingleResult();
			return ultimaOrdem + 1;
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return 1;
		} catch (NonUniqueResultException e) {
			logger.error("Mais de um resultado encontrado", e);
			throw new ViewException("Erro ao buscar elemento único", e);
		}
	}

}
