package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import com.bonsucesso.erp.financeiro.model.Grupo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Grupo.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface GrupoFinder extends BasicEntityFinder<Grupo> {

	public List<Grupo> findAtivos() throws ViewException;

	public Grupo findBy(String descricao) throws ViewException;

}
