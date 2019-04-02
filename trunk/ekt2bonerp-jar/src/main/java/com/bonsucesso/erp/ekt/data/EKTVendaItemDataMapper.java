package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTVendaItem;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTVendaItem.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTVendaItemDataMapper extends DataMapper<EKTVendaItem> {

	public void truncateTable();

}
