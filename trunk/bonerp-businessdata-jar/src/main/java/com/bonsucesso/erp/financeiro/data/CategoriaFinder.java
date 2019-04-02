package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import com.bonsucesso.erp.financeiro.model.Categoria;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Carteira.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface CategoriaFinder extends BasicEntityFinder<Categoria> {

	public List<Categoria> findBy(Categoria pai) throws ViewException;

	public List<Categoria> findBy(String descricao) throws ViewException;

	public Categoria findBy(Long codigo) throws ViewException;

	public List<Categoria> findFolhasBy(String descricao) throws ViewException;

}
