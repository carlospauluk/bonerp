package com.bonsucesso.erp.ekt.data;



import java.util.List;

import com.bonsucesso.erp.ekt.model.EKTEncomendaItem;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade EKTEncomendaItem.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface EKTEncomendaItemFinder extends BasicEntityFinder<EKTEncomendaItem> {

	public List<EKTEncomendaItem> findBy(Double pv) throws ViewException;

	public EKTEncomendaItem findBy(Double numeroNF, Integer recordNumber, Double produto) throws ViewException;

}
