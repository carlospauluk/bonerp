package com.bonsucesso.erp.vendas.spartacus.business;



import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bonsucesso.erp.fiscal.model.NotaFiscal;
import com.bonsucesso.erp.vendas.model.Venda;
import com.ocabit.base.view.exception.ViewException;


public interface ISpartacusHandler {

	ISpartacusHandler getThiz();

	JdbcTemplate getJdbcTemplate();

	void setJdbcTemplate(JdbcTemplate jdbcTemplate);

	/**
	 * 
	 * @throws ViewException
	 */
	void checkPV(Venda pv) throws ViewException;

	/**
	 * Deleta para inserir novamente (nos casos de refaturamento).
	 * 
	 * @throws ViewException
	 */
	void apagarMensagens(NotaFiscal nf) throws ViewException;

	/**
	 * Salva os dados no Postgres da Spartacus.
	 * 
	 * @param xml
	 * @throws ViewException
	 */
	void faturarPV(Venda pv) throws ViewException;

	/**
	 * Salva os dados no Postgres da Spartacus.
	 * 
	 * @param xml
	 * @throws ViewException
	 */
	void faturar(NotaFiscal nf) throws ViewException;

	/**
	 * Salva o registro na tabela do cabeçalho da nota.
	 * 
	 * @param pv
	 * @throws ViewException
	 */
	Integer saveCabecalho(NotaFiscal nf) throws ViewException;

	/**
	 * Quando a nota fiscal referencia outra, é preciso salvar em outra tabela: nfrefv
	 * 
	 * @param nf
	 * @throws ViewException
	 */
	void saveNfReferenciada(NotaFiscal nf) throws ViewException;

	/**
	 * Salva o registro na tabela de itens da nota.
	 * 
	 * @param itens
	 * @throws ViewException
	 */
	void saveItens(Integer idNotaGerada, NotaFiscal nf) throws ViewException;

	/**
	 * Envia mensagem para o banco postgres para reimprimir.
	 * 
	 * @param venda
	 * @throws ViewException
	 */
	void reimprimir(NotaFiscal nf) throws ViewException;

	/**
	 * Envia mensagem para o banco postgres para reimprimir.
	 * 
	 * @param venda
	 * @throws ViewException
	 */
	void cancelar(NotaFiscal nf) throws ViewException;

	Campo findCampoByNome(String nome, List<Campo> campos);

	/**
	 * Constrói o INSERT.
	 * 
	 * @param nomeTabela
	 * @param campos
	 * @return
	 */
	String buildInsert(String nomeTabela, List<Campo> campos);

	/**
	 * Constrói o INSERT.
	 * 
	 * @param nomeTabela
	 * @param campos
	 * @return
	 * @throws ViewException
	 */
	String buildUpdate(String nomeTabela, List<Campo> campos, Map<String, Object> chavesPrimarias)
			throws ViewException;

	/**
	 * Atualiza os status de todas as NotaFiscal de vendas do BonERP.
	 * 
	 * @throws ViewException
	 */
	void atualizarStatus(Date dtVenda) throws ViewException;

	/**
	 * Atualiza os status de todas as NotaFiscal de vendas do BonERP.
	 * 
	 * @throws ViewException
	 */
	NotaFiscal atualizarStatus(NotaFiscal nf) throws ViewException;

	List<Campo> buildCamposCabecalho() throws ViewException;

	List<Campo> buildCamposItem();
	
	public List<Map<String, Object>> getXMLs(Date mesAno) throws ViewException;

	public Map<String, Object> getXML(Integer spartacusId) throws ViewException;

}
