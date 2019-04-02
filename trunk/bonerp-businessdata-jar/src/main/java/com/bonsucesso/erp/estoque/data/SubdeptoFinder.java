package com.bonsucesso.erp.estoque.data;



import java.util.List;

import com.bonsucesso.erp.estoque.model.Departamento;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Subdepartamento.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface SubdeptoFinder extends BasicEntityFinder<Subdepartamento> {

	public List<SubdeptoFinder> findSubdeptosByDepto(Departamento depto);

	public Subdepartamento findByNome(String nome) throws ViewException;

	public Subdepartamento findByCodigo(Integer codigo) throws ViewException;
	
	public List<Subdepartamento> findByFornecedor(Fornecedor fornecedor) throws ViewException;

}
