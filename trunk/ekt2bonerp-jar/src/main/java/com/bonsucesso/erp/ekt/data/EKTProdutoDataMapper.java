package com.bonsucesso.erp.ekt.data;



import com.bonsucesso.erp.ekt.model.EKTProduto;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade EKTProduto.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface EKTProdutoDataMapper extends DataMapper<EKTProduto> {
	
	public void truncateTable();

}
