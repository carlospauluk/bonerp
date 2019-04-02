package com.bonsucesso.erp.financeiro.data;



import com.bonsucesso.erp.financeiro.model.BandeiraCartao;
import com.bonsucesso.erp.financeiro.model.Modo;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade BandeiraCartao.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface BandeiraCartaoFinder extends BasicEntityFinder<BandeiraCartao> {

	public BandeiraCartao findBy(String descricao) throws ViewException;

	public BandeiraCartao findByLabelsAndModo(String str, Modo modo) throws ViewException;

}
