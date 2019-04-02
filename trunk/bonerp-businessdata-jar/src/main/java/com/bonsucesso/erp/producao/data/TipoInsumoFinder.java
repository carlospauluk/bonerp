package com.bonsucesso.erp.producao.data;



import com.bonsucesso.erp.producao.model.TipoInsumo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade TipoInsumo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface TipoInsumoFinder extends BasicEntityFinder<TipoInsumo> {

	public TipoInsumo findByCodigo(Integer codigo) throws ViewException;

}
