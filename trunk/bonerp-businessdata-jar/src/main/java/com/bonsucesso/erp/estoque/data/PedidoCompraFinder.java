package com.bonsucesso.erp.estoque.data;



import java.math.BigDecimal;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.PedidoCompra;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Subdepartamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface PedidoCompraFinder extends BasicEntityFinder<PedidoCompra> {

	public BigDecimal findQtdePedidaBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException;

	public BigDecimal findQtdePedidaBy(Produto produto) throws ViewException;

}
