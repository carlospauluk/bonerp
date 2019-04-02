package com.bonsucesso.erp.producao.data;



import java.util.List;

import com.bonsucesso.erp.crm.model.Cliente;
import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.producao.model.Instituicao;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Instituicao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface InstituicaoFinder extends BasicEntityFinder<Instituicao> {

	public Instituicao findByCodigo(Integer codigo) throws ViewException;

	public List<Instituicao> findBy(String nome) throws ViewException;

	public Integer findNextCodigo() throws ViewException;

	/**
	 * Retorna uma lista de clientes possíveis de serem vinculados (Cliente 1<->1 Instituição).
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<Cliente> findVinculosClientesPossiveis(String str) throws ViewException;

	/**
	 * Retorna uma lista de fornecedores possíveis de serem vinculados (Cliente 1<->1 Instituição).
	 *
	 * @return
	 * @throws ViewException
	 */
	public List<Fornecedor> findVinculosFornecedoresPossiveis(String str) throws ViewException;

	/**
	 * Retorna todas as instituições que tem um fornecedor vinculado.
	 * 
	 * @return
	 * @throws ViewException
	 */
	public List<Instituicao> findInstituicoesFornecedores() throws ViewException;

}
