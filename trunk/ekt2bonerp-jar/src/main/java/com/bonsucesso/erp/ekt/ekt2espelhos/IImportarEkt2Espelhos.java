package com.bonsucesso.erp.ekt.ekt2espelhos;



import java.io.Serializable;
import java.sql.Connection;
import java.util.Set;

import com.ocabit.base.model.EntityId;
import com.ocabit.base.view.exception.ViewException;


public interface IImportarEkt2Espelhos extends Serializable {

	/**
	 * Importar os registros do arquivo EST_D002 para a tabela ekt_deptos.
	 *
	 * @throws ViewException
	 */
	void importarFornecedores() throws ViewException;

	/**
	 * Importar os registros do arquivo EST_D003 para a tabela ekt_deptos.
	 *
	 * @throws ViewException
	 */
	void importarDeptos() throws ViewException;

	/**
	 * Importar os registros do arquivo EST_D004 para a tabela ekt_subdeptos.
	 *
	 * @throws ViewException
	 */
	void importarSubDeptos() throws ViewException;

	/**
	 * Importar os registros do arquivo EST_D005 para a tabela ekt_grade.
	 *
	 * @throws ViewException
	 */
	void importarGrades() throws ViewException;

	/**
	 * Importar os registros do arquivo EST_D006 para a tabela ekt_produtos.
	 *
	 * @throws ViewException
	 */
	void importarProdutos() throws ViewException;

	/**
	 * Importar os registros do arquivo EST_D008 para a tabela ekt_vendedor.
	 *
	 * @throws ViewException
	 */
	void importarVendedores() throws ViewException;

	/**
	 * Importar os registros do arquivo PED_D020 para a tabela ekt_pedidos.
	 *
	 * @throws ViewException
	 */
	void importarPedidos() throws ViewException;

	/**
	 * Importar os registros do arquivo PED_D020 para a tabela ekt_pedidos.
	 *
	 * @throws ViewException
	 */
	void importarEncomendas() throws ViewException;

	/**
	 * Importar os registros do arquivo PED_D020 para a tabela ekt_pedidos.
	 *
	 * @throws ViewException
	 */
	void importarEncomendaItem() throws ViewException;

	/**
	 * Importar os registros do arquivo VEN_D053 para a tabela ekt_plano_crediario.
	 *
	 * @throws ViewException
	 */
	void importarPlanosCrediario() throws ViewException;

	/**
	 * Importar os registros do arquivo VEN_D060 para a tabela ekt_venda.
	 *
	 * @throws ViewException
	 */
	void importarVendas() throws ViewException;

	/**
	 * Importar os registros do arquivo VEN_D061 para a tabela ekt_venda_item.
	 *
	 * @throws ViewException
	 */
	void importarVendasItens() throws ViewException;

	/**
	 * Importar os registros do arquivo VEN_D062 para a tabela ekt_plano_pagto.
	 *
	 * @throws ViewException
	 */
	void importarPlanosPagto() throws ViewException;

	void handleIudtUserInfo(EntityId e);

	boolean checkColumns(String table, Set<String> colunasEsperadas) throws ViewException;

	IImportarEkt2Espelhos getThiz();

	void setThiz(IImportarEkt2Espelhos thiz);

	void deletarProdutosMesAno() throws ViewException;

	void setTipoImportacao(TipoImportacao tipoImportacao);

	TipoImportacao getTipoImportacao();

	Connection getConn();

	void deletarVendasDoMesAno() throws ViewException;

	void deletarVendasItensDoMesAno() throws ViewException;

}
