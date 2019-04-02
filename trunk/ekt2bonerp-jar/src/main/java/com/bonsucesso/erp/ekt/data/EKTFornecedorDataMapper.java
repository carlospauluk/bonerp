package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTFornecedor;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTFornecedor.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTFornecedorDataMapper extends DataMapper<EKTFornecedor> {

	public void truncateTable();

	public void deleteByMesano(String mesano);

}
