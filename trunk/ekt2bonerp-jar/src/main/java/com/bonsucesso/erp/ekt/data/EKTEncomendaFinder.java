package com.bonsucesso.erp.ekt.data;



import java.util.List;

import com.bonsucesso.erp.ekt.model.EKTEncomenda;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTEncomenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTEncomendaFinder extends BasicEntityFinder<EKTEncomenda> {

	public List<EKTEncomenda> findAllValidas(int inicio, int fim) throws ViewException;

	public EKTEncomenda findBy(Double numero) throws ViewException;

}
