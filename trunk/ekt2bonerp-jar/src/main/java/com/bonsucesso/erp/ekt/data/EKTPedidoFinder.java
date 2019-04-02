package com.bonsucesso.erp.ekt.data;



import java.util.List;

import com.bonsucesso.erp.ekt.model.EKTPedido;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTPedidoFinder extends BasicEntityFinder<EKTPedido> {

	/**
	 * Retorna uma lista pois a tabela de pedidos do EKT não é normalizada.
	 * 
	 * @param numero
	 * @return
	 * @throws ViewException
	 */
	public List<EKTPedido> findBy(Double numero) throws ViewException;

}
