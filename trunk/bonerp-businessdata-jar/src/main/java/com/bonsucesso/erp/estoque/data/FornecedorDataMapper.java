package com.bonsucesso.erp.estoque.data;



import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.data.DataMapper;


/**
 * Definição de DataMapper para entidade Fornecedor.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface FornecedorDataMapper extends DataMapper<Fornecedor> {

	public Integer getProximoCodigo();

}
