package com.bonsucesso.erp.estoque.data;



import java.math.BigDecimal;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.DepreciacaoPreco;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.jpa.utils.JPAUtil;


/**
 * Implementação de Finder para entidade DepreciacaoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("depreciacaoPrecoFinder")
public class DepreciacaoPrecoFinderImpl extends BasicEntityFinderImpl<DepreciacaoPreco> implements
		DepreciacaoPrecoFinder {

	/**
	 *
	 */
	private static final long serialVersionUID = -673356917961085295L;

	protected static Logger logger = Logger.getLogger(DepreciacaoPrecoFinderImpl.class);

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public BigDecimal findDepreciacaoByPrazo(Integer prazo) throws ViewException {
		try {
			final String jpql = "FROM DepreciacaoPreco WHERE prazoIni <= :prazo AND prazoFim >= :prazo";
			final Query qry = getEntityManager().createQuery(jpql);
			JPAUtil.cacheOn(qry);
			qry.setParameter("prazo", prazo);
			DepreciacaoPreco depreciacaoPreco = (DepreciacaoPreco) qry.getSingleResult();
			return depreciacaoPreco.getPorcentagem();
		} catch (NoResultException e) {
			logger.debug("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar depreciação por prazo.", e);
		}
	}
}
