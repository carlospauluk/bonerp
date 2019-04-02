package com.bonsucesso.erp.ekt.data;



import java.util.List;

import com.bonsucesso.erp.ekt.model.EKTVendaItem;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTVendaItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTVendaItemFinder extends BasicEntityFinder<EKTVendaItem> {

	public List<EKTVendaItem> findBy(Double pv, String mesano) throws ViewException;

	EKTVendaItem findBy(Double numeroNF, Integer recordNumber, Double produto, String mesano) throws ViewException;

	List<EKTVendaItem> findAllBy(String mesano) throws ViewException;

}
