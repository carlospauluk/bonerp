package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTVenda;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTVenda.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTVendaDataMapper extends DataMapper<EKTVenda> {

	public void truncateTable();

}
