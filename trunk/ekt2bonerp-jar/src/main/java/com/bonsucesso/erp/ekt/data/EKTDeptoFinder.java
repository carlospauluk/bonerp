package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTDepto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTDepto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTDeptoFinder extends BasicEntityFinder<EKTDepto> {

	public EKTDepto findByCodigo(Double codigo) throws ViewException;

}
