package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTSubDepto;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTSubDepto.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTSubDeptoDataMapper extends DataMapper<EKTSubDepto> {

	public void truncateTable();

}
