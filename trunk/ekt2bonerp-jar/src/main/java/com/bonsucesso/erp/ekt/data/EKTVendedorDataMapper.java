package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTVendedor;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTVendedor.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTVendedorDataMapper extends DataMapper<EKTVendedor> {

	public void truncateTable();

}
