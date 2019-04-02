package com.bonsucesso.erp.ekt.data;



import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bonsucesso.erp.ekt.model.EKTPedido;
import com.ocabit.base.data.BasicEntityFinderImpl;
import com.ocabit.base.view.exception.ViewException;


/**
 * Implementação de Finder para entidade EKTVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
@Component("ektPedidoFinder")
public class EKTPedidoFinderImpl extends BasicEntityFinderImpl<EKTPedido> implements EKTPedidoFinder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8573134178625682461L;

	@Override
	@Transactional(transactionManager = "txManager", propagation = Propagation.REQUIRES_NEW)
	public List<EKTPedido> findBy(Double numero) throws ViewException {
		return getThiz().findResults("FROM EKTPedido WHERE pedido = :numero", "numero", numero);
	}

}
