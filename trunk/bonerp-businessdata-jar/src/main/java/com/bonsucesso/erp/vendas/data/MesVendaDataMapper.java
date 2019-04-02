package com.bonsucesso.erp.vendas.data;



import java.util.Date;

import com.bonsucesso.erp.vendas.model.MesVenda;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade MesVenda.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface MesVendaDataMapper extends DataMapper<MesVenda> {

	public void gerarProximoMes() throws ViewException;

	public void gerarMesVendaAte(Date mesAno) throws ViewException;

}
