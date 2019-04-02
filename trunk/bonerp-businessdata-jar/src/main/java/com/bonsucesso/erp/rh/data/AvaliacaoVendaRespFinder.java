package com.bonsucesso.erp.rh.data;



import com.bonsucesso.erp.rh.model.AvaliacaoVendaResp;
import com.bonsucesso.erp.rh.model.AvaliacaoVendaRespQuestao;
import com.ocabit.base.data.BasicEntityFinder;
import com.ocabit.base.view.exception.ViewException;


/**
 * Definição de Finder para a entidade AvaliacaoVendaResp.
 *
 * @author Carlos Eduardo Pauluk
 *
 */
public interface AvaliacaoVendaRespFinder extends BasicEntityFinder<AvaliacaoVendaResp> {

	public AvaliacaoVendaRespQuestao findBy(AvaliacaoVendaResp avaliacaoVendaResp, Integer ordem) throws ViewException;

}
