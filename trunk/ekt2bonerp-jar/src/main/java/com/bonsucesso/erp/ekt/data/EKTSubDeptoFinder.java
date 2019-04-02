package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTSubDepto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTDepto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTSubDeptoFinder extends BasicEntityFinder<EKTSubDepto> {

	public EKTSubDepto findByCodigo(Double codigo) throws ViewException;

}
