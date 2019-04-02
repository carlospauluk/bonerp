package com.bonsucesso.erp.vendas.data;



import com.bonsucesso.erp.vendas.model.PlanoPagto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade PlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface PlanoPagtoFinder extends BasicEntityFinder<PlanoPagto> {

	public PlanoPagto findBy(String codigo) throws ViewException;
	
	public PlanoPagto findByDescricao(String descricao) throws ViewException;

}
