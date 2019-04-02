package com.bonsucesso.erp.estoque.data;



import java.math.BigDecimal;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.PedidoCompra;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade PedidoCompra.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("pedidoCompraFinder")
public class PedidoCompraFinderImpl extends BasicEntityFinderImpl<PedidoCompra> implements PedidoCompraFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4926212291190034613L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRED, readOnly = true)
	public BigDecimal findQtdePedidaBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException {
		try {
			final String jpql = "SELECT sum(qtde) FROM PedidoCompraItem i WHERE i.pedidoCompra.fornecedor = :fornecedor AND i.subdepto = :subdepto AND i.status = 'AGUARDANDO_ENTREGA'";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("fornecedor", fornecedor);
			qry.setParameter("subdepto", subdepto);
			return (BigDecimal) qry.getSingleResult();
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
	public BigDecimal findQtdePedidaBy(Produto produto) throws ViewException {
		try {
			final String jpql = "SELECT sum(qtde) FROM PedidoCompraItem i WHERE i.produto = :produto AND i.status = 'AGUARDANDO_ENTREGA'";
			final Query qry = getEntityManager().createQuery(jpql);
			qry.setParameter("produto", produto);
			return (BigDecimal) qry.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Nenhum resultado encontrado");
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new ViewException(e);
		}
		
	}

}
