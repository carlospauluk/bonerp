package com.bonsucesso.erp.financeiro.data;



import com.bonsucesso.erp.financeiro.model.CentroCusto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade CentroCusto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface CentroCustoFinder extends BasicEntityFinder<CentroCusto> {

	public CentroCusto findBy(Integer codigo) throws ViewException;

}
