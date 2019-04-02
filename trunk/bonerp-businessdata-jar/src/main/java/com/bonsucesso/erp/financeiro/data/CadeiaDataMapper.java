package com.bonsucesso.erp.financeiro.data;



import com.bonsucesso.erp.financeiro.model.Cadeia;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Cadeia.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface CadeiaDataMapper extends DataMapper<Cadeia> {

	public Cadeia saveCadeiaEMovimentacoes(Cadeia cadeia) throws ViewException;

}
