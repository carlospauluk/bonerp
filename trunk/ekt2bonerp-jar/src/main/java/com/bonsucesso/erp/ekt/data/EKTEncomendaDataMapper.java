package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTEncomenda;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTEncomenda.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTEncomendaDataMapper extends DataMapper<EKTEncomenda> {

	public void truncateTable();

}
