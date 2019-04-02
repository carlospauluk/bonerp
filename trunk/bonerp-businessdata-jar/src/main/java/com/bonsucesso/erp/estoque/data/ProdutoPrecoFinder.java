package com.bonsucesso.erp.estoque.data;



import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoPreco;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade ProdutoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface ProdutoPrecoFinder extends BasicEntityFinder<ProdutoPreco> {

	public ProdutoPreco findBy(Produto produto, Double precoCusto, Double precoVista, Double precoPrazo)
			throws ViewException;

}
