package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTDepto;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTDepto.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTDeptoDataMapper extends DataMapper<EKTDepto> {
	
	public void truncateTable();

}
