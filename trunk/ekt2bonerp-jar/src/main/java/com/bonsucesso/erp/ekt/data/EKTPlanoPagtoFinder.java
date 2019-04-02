package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTPlanoPagto;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTPlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTPlanoPagtoFinder extends BasicEntityFinder<EKTPlanoPagto> {

	public EKTPlanoPagto findByCodigo(String codigo) throws ViewException;

}
