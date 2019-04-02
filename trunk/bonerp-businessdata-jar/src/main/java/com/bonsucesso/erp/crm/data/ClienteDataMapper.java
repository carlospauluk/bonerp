package com.bonsucesso.erp.crm.data;



import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade Cliente.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface ClienteDataMapper extends DataMapper<Cliente> {

	public Integer getProximoCodigo();

}
