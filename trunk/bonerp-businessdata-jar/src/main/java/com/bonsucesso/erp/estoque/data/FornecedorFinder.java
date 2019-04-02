package com.bonsucesso.erp.estoque.data;



import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.base.model.Pessoa;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Fornecedor.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface FornecedorFinder extends BasicEntityFinder<Fornecedor> {

	public List<Fornecedor> findByStr(String nomes) throws ViewException;

	public Fornecedor findByNomeFantasia(String nomeFantasia) throws ViewException;

	public Fornecedor findByCodigo(Integer codigo) throws ViewException;

	public Fornecedor findByDoc(String doc) throws ViewException;

	public Fornecedor findByCodigoEKT(Integer codigoEKT, Date dtImportacao) throws ViewException;

	public Fornecedor findBy(Pessoa pessoa) throws ViewException;

	public List<Fornecedor> findAtuaisByTipo(String tipoFornecedorDescricao) throws ViewException;

}
