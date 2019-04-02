package com.bonsucesso.erp.vendas.data;



import com.bonsucesso.erp.vendas.model.Encomenda;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Encomenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EncomendaFinder extends BasicEntityFinder<Encomenda> {

	public Encomenda findBy(Integer numero) throws ViewException;

}
