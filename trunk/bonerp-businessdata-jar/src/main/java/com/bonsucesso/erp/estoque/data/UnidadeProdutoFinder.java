package com.bonsucesso.erp.estoque.data;



import com.bonsucesso.erp.estoque.model.UnidadeProduto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade UnidadeProduto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface UnidadeProdutoFinder extends BasicEntityFinder<UnidadeProduto> {

	public UnidadeProduto findByLabel(String label) throws ViewException;

}
