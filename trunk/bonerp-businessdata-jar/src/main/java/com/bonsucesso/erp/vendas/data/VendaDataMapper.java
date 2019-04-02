package com.bonsucesso.erp.vendas.data;



import java.util.Date;

import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Venda.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface VendaDataMapper extends DataMapper<Venda> {

	public void deleteTodosBy(Date dtVenda) throws ViewException;

	public Venda save_RN(Venda venda) throws ViewException;

}
