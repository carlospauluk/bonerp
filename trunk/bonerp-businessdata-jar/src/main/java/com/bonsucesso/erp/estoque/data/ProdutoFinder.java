package com.bonsucesso.erp.estoque.data;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bonsucesso.erp.estoque.model.Fornecedor;
import com.bonsucesso.erp.estoque.model.Produto;
import com.bonsucesso.erp.estoque.model.ProdutoSaldo;
import com.bonsucesso.erp.estoque.model.Subdepartamento;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade Produto.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface ProdutoFinder extends BasicEntityFinder<Produto> {

	public List<Produto> findByStr(String str) throws ViewException;

	public Produto findByReduzido(Long reduzido) throws ViewException;

	public List<Produto> findByReduzidoEkt(Integer reduzidoEkt, Date dtImportacao) throws ViewException;

	public Produto findProximo(Produto produto) throws ViewException;

	public Produto findAnterior(Produto produto) throws ViewException;

	public ProdutoSaldo findSaldo(Produto produto, String tamanho) throws ViewException;

	public BigDecimal findQtdeBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException;

	public List<Produto> findAtuaisBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException;

	@SuppressWarnings("rawtypes")
	public List findProdutosSemReduzidoEktAteNull() throws ViewException;

	public Long findProximoReduzido() throws ViewException;

	public String findProximaReferencia(Produto p) throws ViewException;

	public BigDecimal findQtdeBy(Produto produto) throws ViewException;

	
	/**
	 * Encontra todos os produtos que não sejam artigos de cortina.
	 * 
	 * @param subdepto
	 * @return
	 * @throws ViewException
	 */
	public List<Produto> findNaoArtigosCortinaBy(String str, List<Subdepartamento> subdeptos) throws ViewException;

	/**
	 * Retorna todos os produtos com saldo positivo ou com dtUltVenda de um ano atrás pra cá.
	 * @return
	 * @throws ViewException
	 */
	public List<Produto> findProdutosAtivos() throws ViewException;

	/**
	 * Retorna somente os fornecedores e subdeptos dos produtos ativos, já agrupados.
	 * @return
	 * @throws ViewException
	 */
	public List<Object[]> findFornecedoresSubdeptosDeProdutosAtivos() throws ViewException;

	/**
	 * Soma a qtde de itens em estoque (ignorando os saldos negativos) para o fornecedor e subdepto.
	 * 
	 * @param fornecedor
	 * @param subdepto
	 * @return
	 * @throws ViewException
	 */
	public BigDecimal findQtdeEmEstoqueBy(Fornecedor fornecedor, Subdepartamento subdepto) throws ViewException;

}
