package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTEncomendaItem;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTEncomendaItem.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTEncomendaItemDataMapper extends DataMapper<EKTEncomendaItem> {

	public void truncateTable();

}
