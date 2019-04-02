package com.bonsucesso.erp.fiscal.data;



import com.bonsucesso.erp.fiscal.model.MensagemRetornoRF;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade MensagemRetornoRF.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface MensagemRetornoRFFinder extends BasicEntityFinder<MensagemRetornoRF> {

	public MensagemRetornoRF findBy(Integer codigo) throws ViewException;

}
