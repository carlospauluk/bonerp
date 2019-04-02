package com.bonsucesso.erp.rh.data;



import com.bonsucesso.erp.rh.model.AvaliacaoVenda;
import com.bonsucesso.erp.rh.model.AvaliacaoVendaQuestao;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade AvaliacaoVenda.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface AvaliacaoVendaFinder extends BasicEntityFinder<AvaliacaoVenda> {

	public AvaliacaoVendaQuestao findBy(AvaliacaoVenda avaliacaoVenda, Integer ordem) throws ViewException;

}
