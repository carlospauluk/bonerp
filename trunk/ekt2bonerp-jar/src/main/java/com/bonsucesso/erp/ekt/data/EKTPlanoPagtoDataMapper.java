package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTPlanoPagto;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTPlanoPagto.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTPlanoPagtoDataMapper extends DataMapper<EKTPlanoPagto> {

	public void truncateTable();

}
