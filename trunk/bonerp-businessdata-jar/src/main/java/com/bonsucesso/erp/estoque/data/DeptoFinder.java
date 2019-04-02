package com.bonsucesso.erp.estoque.data;



import java.util.List;

import com.bonsucesso.erp.estoque.model.Departamento;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Datatables.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface DeptoFinder extends BasicEntityFinder<Departamento> {

	public Departamento findByCodigo(Integer codigo) throws ViewException;

	public List<Departamento> findDeptosArtigosCortina() throws ViewException;

}
