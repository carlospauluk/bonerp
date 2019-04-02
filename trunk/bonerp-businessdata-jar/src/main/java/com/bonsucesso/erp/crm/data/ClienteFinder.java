package com.bonsucesso.erp.crm.data;



import java.util.List;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.crm.model.Cliente;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Cliente.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface ClienteFinder extends BasicEntityFinder<Cliente> {

	public List<Cliente> findByStr(String str) throws ViewException;

	public Cliente findClienteByDoc(String doc) throws ViewException;

	public Cliente findByPessoa(Pessoa pessoa) throws ViewException;

	public 	List<Cliente> findByNome(String nome) throws ViewException;

	public List<Cliente> findSomenteComCelular() throws ViewException;

}
