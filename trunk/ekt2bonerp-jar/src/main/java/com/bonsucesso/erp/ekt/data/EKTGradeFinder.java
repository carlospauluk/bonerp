package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTGrade;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTDepto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTGradeFinder extends BasicEntityFinder<EKTGrade> {

	public EKTGrade findByCodigo(Double codigo) throws ViewException;

}
