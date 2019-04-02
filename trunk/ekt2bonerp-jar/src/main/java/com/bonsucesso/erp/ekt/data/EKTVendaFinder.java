package com.bonsucesso.erp.ekt.data;



import java.util.List;

import com.bonsucesso.erp.ekt.model.EKTVenda;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTVendaFinder extends BasicEntityFinder<EKTVenda> {

	public List<EKTVenda> findAllValidas(int inicio, int fim, String mesano) throws ViewException;

	public EKTVenda findBy(Double numero, String mesano) throws ViewException;

}
