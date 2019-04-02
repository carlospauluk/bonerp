package com.bonsucesso.erp.producao.data;



import com.bonsucesso.erp.producao.model.Insumo;
import com.bonsucesso.erp.producao.model.InsumoPreco;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Insumo.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface InsumoDataMapper extends DataMapper<Insumo> {

	public Insumo saveComPreco(Insumo insumo, InsumoPreco insumoPreco) throws ViewException;

}
