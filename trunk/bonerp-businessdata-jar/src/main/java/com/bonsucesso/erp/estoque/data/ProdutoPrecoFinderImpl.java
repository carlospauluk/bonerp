package com.bonsucesso.erp.estoque.data;



import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;
import com.ocabit.utils.currency.CurrencyUtils;


/**
 * Implementação de Finder para entidade ProdutoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("produtoPrecoFinder")
public class ProdutoPrecoFinderImpl extends BasicEntityFinderImpl<ProdutoPreco> implements ProdutoPrecoFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8397442503794068544L;

	protected static Logger logger = Logger.getLogger(ProdutoPrecoFinderImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public ProdutoPreco findBy(Produto produto, Double precoCusto, Double precoVista, Double precoVenda)
			throws ViewException {
		try {
			final String jpql = "FROM ProdutoPreco WHERE " +
					"produto = :produto AND " +
					"precoCusto = :precoCusto AND " +
					// "precoVista = :precoVista AND " +
					"(precoPrazo = :precoVenda OR precoPromo = :precoVenda)";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("produto", produto);
			qry.setParameter("precoCusto", CurrencyUtils.getBigDecimalCurrency(precoCusto) );
			// qry.setParameter("precoVista", CurrencyUtils.getBigDecimalCurrency(precoVista) );
			qry.setParameter("precoVenda", CurrencyUtils.getBigDecimalCurrency(precoVenda) );

			List<ProdutoPreco> rs = qry.getResultList();
			if (rs != null && rs.size() > 0) {
				return rs.get(0);
			} else {
				return null;
			}
		} catch (NoResultException e) {
			logger.info("Preço não encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException("Erro ao pesquisar produto.", e);
		}
	}

}
