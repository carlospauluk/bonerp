package com.bonsucesso.erp.financeiro.data;



import com.bonsucesso.erp.financeiro.model.Carteira;
import com.bonsucesso.erp.financeiro.model.OperadoraCartao;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade OperadoraCartao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface OperadoraCartaoFinder extends BasicEntityFinder<OperadoraCartao> {

	public OperadoraCartao findBy(String descricao) throws ViewException;

	public OperadoraCartao findBy(Carteira carteira) throws ViewException;

}
