package com.bonsucesso.erp.financeiro.data;



import java.util.List;

import com.bonsucesso.erp.financeiro.model.Movimentacao;
import com.ocabit.base.data.DataMapper;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de DataMapper para entidade Movimentacao.
 *
 * @author Carlos Eduardo Pauluk
 */
public interface MovimentacaoDataMapper extends DataMapper<Movimentacao> {

	public Movimentacao saveTransfPropria(Movimentacao movimentacao) throws ViewException;

	public Movimentacao saveTransfCaixa(Movimentacao movimentacao) throws ViewException;

	public List<String> processarRecorrentes(List<Movimentacao> movimentacoes) throws ViewException;

	public String processarRecorrente(Movimentacao originante) throws ViewException;

	@Override
	public void saveList(List<Movimentacao> lista) throws ViewException;

	//	public void saveParcelas(List<Movimentacao> parcelas) throws ViewException;

	/**
	 * Verifica o tipo de lancto da movimentacao para saber o que fazer.
	 *
	 * @param movimentacao
	 * @return
	 * @throws ViewException
	 */
	public Movimentacao processSaves(Movimentacao movimentacao) throws ViewException;

	public void deleteList(List<Movimentacao> list) throws ViewException;

	public void alterarLoteSave(List<Movimentacao> movimentacoes, Movimentacao movimentacao) throws ViewException;

}
