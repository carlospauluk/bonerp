package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import com.bonsucesso.erp.financeiro.model.Modo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Modo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface ModoFinder extends BasicEntityFinder<Modo> {

	public Modo findBy(String descricao) throws ViewException;

	public Modo findBy(Integer codigo) throws ViewException;

	public List<Modo> findAllTransfPropria() throws ViewException;

	public List<Modo> findAllTransfCaixa() throws ViewException;

}
