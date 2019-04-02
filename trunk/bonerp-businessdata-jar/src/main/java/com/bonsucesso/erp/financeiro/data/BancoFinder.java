package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import com.bonsucesso.erp.financeiro.model.Banco;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Banco.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface BancoFinder extends BasicEntityFinder<Banco> {

	public List<Banco> findBy(String str, Boolean utilizado) throws ViewException;

	public Banco findBy(Integer codigo) throws ViewException;

}
