package com.bonsucesso.erp.estoque.data;



import java.math.BigDecimal;

import com.bonsucesso.erp.estoque.model.DepreciacaoPreco;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade DepreciacaoPreco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface DepreciacaoPrecoFinder extends BasicEntityFinder<DepreciacaoPreco> {

	public BigDecimal findDepreciacaoByPrazo(Integer prazo) throws ViewException;

}
